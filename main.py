from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import subprocess
import logging
import json

app = FastAPI(title="Arabic Morphological Analyzer API")

class TextRequest(BaseModel):
    text: str

@app.post("/analyze")
async def analyze_text(request: TextRequest):
    input_text = request.text.strip()
    if not input_text:
        raise HTTPException(status_code=400, detail="Input text is empty")

    # Function to remove specific characters like 'ٰ' and 'ٓ'
    def clean_word(word):
        return word.replace('ٰ', '').replace('ٓ', '').replace('ـٔ', 'ء').replace('ئ', 'ءي')

    # Ensure the first word and all words are processed correctly
    words = input_text.split()
    results = []
    for word in words:
        word = clean_word(word)  # Remove specific characters
        try:
            # Log the full command being executed
            command = [
                "java",
                "-cp",
                "E:\\Quran Computing Institute\\Al-Khalil\\Tools\\AlkhalilMorphSys2\\src\\net\\oujda_nlp_team\\json\\json-20140107.jar;E:\\Quran Computing Institute\\Al-Khalil\\Tools\\AlkhalilMorphSys2\\src",
                "net.oujda_nlp_team.AlKhalil2AnalyzerWrapper",
                word
            ]
            logging.debug(f"Executing command: {' '.join(command)}")

            # Call the Java process for each word
            result = subprocess.run(
                command,
                capture_output=True, text=True, check=True
            )
            output = result.stdout.strip() if result.stdout else ""

            logging.info(f"Java process output for word '{word}': {output}")

            # Log any error output from the Java process
            if result.stderr:
                logging.error(f"Java process error for word '{word}': {result.stderr}")

            # Extract JSON part from the output
            json_start = output.find('[')
            json_end = output.rfind(']') + 1
            if json_start == -1 or json_end == -1:
                logging.warning(f"No JSON found in output for word '{word}': {output}")
                results.append({"Word": word, "Root": "-", "Lemma": "-", "Part Of Speech": "-", "Pattern Stem": "-"})
                continue
            json_output_str = output[json_start:json_end].strip()

            # Parse the JSON output
            try:
                json_output = json.loads(json_output_str)
                if not json_output:
                    logging.warning(f"Empty JSON result for word '{word}': {json_output_str}")
                    results.append({"Word": word, "Root": "-", "Lemma": "-", "Part Of Speech": "-", "Pattern Stem": "-"})
                else:
                    results.extend(json_output)
            except json.JSONDecodeError as e:
                logging.error(f"JSON decoding failed for word '{word}': {e}")
                results.append({"Word": word, "Root": "-", "Lemma": "-", "Part Of Speech": "-", "Pattern Stem": "-"})
        except subprocess.CalledProcessError as e:
            logging.error(f"Java process failed for word '{word}': {e.stderr}")
            results.append({"Word": word, "Root": "-", "Lemma": "-", "Part Of Speech": "-", "Pattern Stem": "-"})

    return results 
import jpype.config
import streamlit as st
import requests
import pandas as pd
from pyarabic.araby import tokenize
import jpype
import os
import json


# Set page configuration as the first Streamlit command
st.set_page_config(
    page_title="Al-Khalil Arabic Morphological Analyzer",
    page_icon="qurancomputing.ico",
    layout="wide"
)

st.markdown(
    """
    <style>
    .stTextInput, .stTextArea, .stButton {
        text-align: right;
        color: #FFFFFF;
    }
    .stButton > button {
        background-color: #4CAF50;
        color: white;
    }
    .stButton > button:hover {
        background-color: #45a049;
    }
    .stApp {
        text-align: right;
    }
    .stDownloadButton > button {
        background-color: #4CAF50; /* Green background */
        color: white; /* White text */
    }
    .stDownloadButton > button:hover {
        background-color: #45a049; /* Darker green on hover */
    }
    .logo {
        position: absolute;
        top: 10px;
        right: 10px;
    }
    .center-title {
        text-align: center;
        font-size: 24px;
        margin-bottom: 20px;
    }
    </style>
    """,
    unsafe_allow_html=True
)


# Load API URL from Streamlit secrets
def get_api_url():
    return st.secrets["api"]["url"]

# Use the function to get the API URL
API_URL = get_api_url()

def analyze_sentence(sentence, java_object):
    try:
        # Convert the word to a String array
        word_array = jpype.JArray(jpype.JString)([sentence])
        
        # Keep essential debug for word processing
        # st.write(f"Debug: Analyzing word '{sentence}' with Java method")
        
        # Call the processText method instead of main
        analysis_result = java_object.processText(sentence)
        
        # Keep essential debug for troubleshooting
        # st.write(f"Debug: Raw analysis result type: {type(analysis_result)}")
        # st.write(f"Debug: Raw analysis result: {analysis_result}")
        
        if analysis_result:
            try:
                # Parse the JSON result
                parsed_result = json.loads(str(analysis_result))
                # st.write(f"Debug: Successfully parsed JSON: {parsed_result}")
                return parsed_result
            except json.JSONDecodeError as e:
                st.error(f"JSON decoding error: {str(e)}")
                st.write(f"Debug: Failed JSON string: {analysis_result}")
                return None
        else:
            st.write(f"Debug: No analysis result for word '{sentence}'")
            return None
            
    except Exception as e:
        st.error(f"Unexpected error: {str(e)}")
        st.write(f"Debug: Exception details: {str(e)}")
        return None

def clean_word(word):
        return word.replace('ٰ', '').replace('ٓ', '').replace('ـٔ', 'ء').replace('ئ', 'ءي')
        
def main():


    # Display the logo in the top-right corner
    # st.image("qurancomputing.png", width=50)

    # Central heading
    st.markdown(
        """
        <div class="center-title">
            المعهد العالمي لحوسبة القرآن والعلوم الإسلامية<br>
            QuranComputing.org
        </div>
        """,
        unsafe_allow_html=True
    )

    # Page title
    st.title("الخليل : محلل الصرف للغة العربية\n Al-Khalil Arabic Morphological Analyzer")
    # Add reference URL
    st.markdown("[@http://alkhalil.oujda-nlp-team.net/](http://alkhalil.oujda-nlp-team.net/)")
    st.markdown("[@https://qurancomputing.org](https://qurancomputing.org)")

    # CSS for dark mode and table styling
    st.markdown("""
        <style>
        .stApp {
            background-color: #1E1E1E;
            color: #FFFFFF;
        }
        .output-box {
            background-color: #2D2D2D;
            padding: 20px;
            border-radius: 5px;
            margin: 10px 0;
        }
        .dataframe {
            font-size: 36px; /* Increase font size */
            text-align: right; /* Right-align text for Arabic */
        }
        </style>
    """, unsafe_allow_html=True)

    # Initialize session state for results
    if 'results' not in st.session_state:
        st.session_state.results = []

    st.markdown(
        """
        <style>
        .right-align {
            text-align: right;
            font-weight: bold;
            margin-bottom: 5px;
        }
        .stTextArea label {
            display: block;
            text-align: right;
            font-weight: bold;
            color: #FFFFFF;
            margin-bottom: 5px;
        }
        .stTextArea textarea {
            text-align: right;
        }
        </style>
        """,
        unsafe_allow_html=True
    )

    # Right-aligned heading
    # st.markdown('<div class="right-align">Enter Arabic text for analysis   أدخل النص العربي للتحليل الصرفي</div>', unsafe_allow_html=True)

    # Text input
# Text input
    text_input = st.text_area(
        "Enter Arabic text for analysis   أدخل النص العربي للتحليل الصرفي",
        height=150,
        label_visibility="collapsed"  # Hide the label if needed
    )


    # Analysis button
    if st.button("Analyze Text   حلل النص", key="analyze_button"):
        if text_input:
            with st.spinner("Analyzing text..."):
                sentences = [s.strip() for s in text_input.split('.') if s.strip()]
                results = []

                # Start the JVM
                if not jpype.isJVMStarted():
                    # st.write("Debug: Starting JVM")
                    try:
                        jpype.startJVM(
                            jpype.getDefaultJVMPath(),
                            classpath=[
                                'E:\\Quran Computing Institute\\Al-Khalil\\Tools\\AlkhalilMorphSys2\\src_java',
                                'E:\\Quran Computing Institute\\Al-Khalil\\Tools\\AlkhalilMorphSys2\\src_java\\net\\oujda_nlp_team\\json\\json-20210307.jar'
                            ]
                        )
                        # st.write("Debug: JVM started successfully")
                    except Exception as e:
                        st.error(f"Error starting JVM: {str(e)}")
                        st.write(f"Debug: JVM start error details: {str(e)}")
                        return

                # Access Java classes
                # st.write("Debug: Accessing Java class")
                try:
                    java_class = jpype.JClass("net.oujda_nlp_team.AlKhalil2AnalyzerWrapper")
                    java_object = java_class()
                    # st.write("Debug: Java class accessed successfully")
                except Exception as e:
                    st.error(f"Error accessing Java class: {str(e)}")
                    st.write(f"Debug: Java class access error details: {str(e)}")
                    return

                for sentence in sentences:
                    sentence = sentence.strip()
                    words = sentence.split()
                    for word in words:
                        word = clean_word(word)
                        # st.write(f"Debug: Processing cleaned word: '{word}'")
                        analysis_result = analyze_sentence(word, java_object)
                        # st.write(f"Debug: Analysis result: {analysis_result}")
                        if analysis_result:
                            if isinstance(analysis_result, list):
                                results.extend(analysis_result)
                            else:
                                results.append(analysis_result)
                        else:
                            st.write(f"Debug: No analysis result for word '{word}'")

                st.session_state.results = results  # Store results in session state
                # st.write("Debug: Final results:", results)

    # Shutdown the JVM when done (only in main thread)
    # jpype.shutdownJVM()

    # Debugging output to check the contents of results
    # st.write("Debug: Results before displaying:", st.session_state.results)

    # Display results if available
    if st.session_state.results:
        st.subheader("Analysis Results  نتائج التحليل الصرفي")
        df = pd.DataFrame(st.session_state.results)

        # Define a mapping for column names to Arabic
        column_mapping = {
            "Word": "Word كلمة",
            "Lemma": "Lemma تحليل",
            "Part Of Speech": "Part Of Speech نوع الكلمة",
            "Pattern Stem": "Pattern Stem وزن",
            "Root": "Root جذر"
        }

        # Rename columns
        df.rename(columns=column_mapping, inplace=True)

        df.index = range(1, len(df) + 1)  # Set index starting from 1
        st.dataframe(
            df.style.set_properties(**{'text-align': 'right'}),
            use_container_width=True,
            height=min((len(df) + 1) * 40, 400),
            hide_index=False
        )

        # Add a download button for CSV
        csv = df.to_csv(index=False).encode('utf-8-sig')
        st.download_button(
            label="Download data as CSV UTF-8 تحميل النتائج بصيغة",
            data=csv,
            file_name='analysis_results_utf8.csv',
            mime='text/csv',
        )



if __name__ == "__main__":
    main() 
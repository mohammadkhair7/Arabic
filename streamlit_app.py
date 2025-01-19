import streamlit as st
import requests
import pandas as pd
from pyarabic.araby import tokenize
import os

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

def analyze_sentence(sentence):
    try:
        response = requests.post(API_URL, json={"text": sentence})
        response.raise_for_status()  # Raise an error for bad responses
        return response.json()
    except requests.exceptions.RequestException as e:
        st.error(f"Error: {str(e)}")
        return None

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

                for sentence in sentences:
                    analysis_result = analyze_sentence(sentence)
                    if analysis_result:
                        results.extend(analysis_result)

                st.session_state.results = results  # Store results in session state

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
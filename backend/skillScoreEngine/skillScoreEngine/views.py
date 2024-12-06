from django.shortcuts import render, HttpResponse
import base64
from django.http import JsonResponse
import os
import io
import pdf2image
import pytesseract

from collections import Counter
import re
import google.generativeai as genai
import json
from pdf2image import convert_from_path
from PyPDF2 import PdfReader

genai.configure(api_key='AIzaSyBF3MTHuo4eQK5pDbjNL8npQyz7JKmXeAU')

def extract_text_from_pdf(pdf_path):
    extracted_text = ""

    if os.path.exists(pdf_path):
        # Check if PDF is text-based or image-based
        try:
            # Try reading text from PDF using PyPDF2
            with open(pdf_path, 'rb') as file:
                reader = PdfReader(file)
                for page_num in range(len(reader.pages)):
                    page = reader.pages[page_num]
                    extracted_text += page.extract_text()
        except Exception as e:
            # If text extraction fails, assume the PDF has images and proceed with OCR
            print("Error extracting text using PyPDF2, falling back to OCR:", e)
            with open(pdf_path, 'rb') as file:
                images = convert_from_path(file)
                for page in images:
                    extracted_text += pytesseract.image_to_string(page)  # Use OCR to extract text

        return extracted_text
    else:
        raise FileNotFoundError("No file found at the specified path")



def extract_skills_from_text(text, skill_type="technical"):
    prompt = f"Scan the entire text Extract all {skill_type} skills (technologies, tools, frameworks only) from the entire text: {text}, just return me the keywords of those technolgies, dont say anything else"

    try:
        # Send the prompt to the model via the client
        model = genai.GenerativeModel('gemini-1.5-flash')
        response = model.generate_content(prompt)

        # Print the full response to understand its structure
        print("Full Response:", response)

        # Now, let's handle the response correctly based on the structure
        if hasattr(response, 'candidates') and len(response.candidates) > 0:
            # Extract content from the first candidate
            content = response.candidates[0].content.parts[0].text
            print("Response Content:", content)  # Debug print to see the output

            # Split the content into individual skills
            skills = [skill.strip() for skill in content.split(',')]  # Split by commas and strip extra spaces

            return skills
        else:
            print("No candidates found in the response.")
            return []

    except Exception as e:
        print(f"Error during API call: {e}")
        return []

def getLacksandSkills(job_k, skill_k):
    prompt = f"""
    I will provide two lists:
    1. A list of required skills from a job description: {job_k}
    2. A list of skills a candidate possesses: {skill_k}

    Please compare the two, accounting for synonyms or two keywords can be related (for eg., react and html), and return:
    a) A list of matching skills (handle synonms) (skills the candidate has that are related to skills in the required list).
    b) A list of lacking skills (skills that candidate is not aware of which are required)
    c) A list of skills which are similar to the skills in required list its okay if they are not exact
    Provide the response in JSON, dont use ``` these and dont write the word json, i jus tneed the data
    """
    try:
        # Send the prompt to the model via the client
        model = genai.GenerativeModel('gemini-1.5-flash')
        response = model.generate_content(prompt)

        # Extract the JSON response from the model
        if hasattr(response, 'candidates') and len(response.candidates) > 0:
            content = response.candidates[0].content.parts[0].text
            print("Raw Response Content:", content)  # Debugging
            return content
        else:
            print("No candidates found in the response.")
            return {"matching_skills": [], "lacking_skills": []}

    except Exception as e:
        print(f"Error during API call: {e}")
        return {"matching_skills": [], "lacking_skills": []}

def score(request):
    # Example PDF path (should ideally come from user input)
    pdf_path = "/home/uday/SDP/aTrack/skillScoreEngine/JUday.pdf"
    
    try:
        text = extract_text_from_pdf(pdf_path)
        candidate_skills = extract_skills_from_text(text)
    except FileNotFoundError as e:
        return HttpResponse(str(e), status=404)
    
    # Example job description
    jobdesc = """
    Assist and support in the development of front-end features using HTML, CSS, and JavaScript frameworks (e.g., React, Java, or Python).
    Contribute and participate in back-end development tasks, including API creation, database management, and server-side logic.
    Write and maintain clean, scalable, and well-documented code.
    """
    job_skills = extract_skills_from_text(jobdesc)
    
    result = getLacksandSkills(job_skills, candidate_skills)
    ans_dict = json.loads(result)

    print(type(ans_dict))
    print(ans_dict)
    return JsonResponse(ans_dict)

def home(request):
    return HttpResponse("Home is this")



import os
import openai

openai.api_key = ""

class OpenAiAPI:
    
    def create_name(colour_hex):
        print(colour_hex)
        response = openai.ChatCompletion.create(
            model="gpt-3.5-turbo",
            messages=[
                {"role": "system", "content": "You are a creative assistant."},
                {"role": "user", "content": "Come up with a fun name that describes the color #ffffff."}
            ],
            temperature=1,
            max_tokens=20
        )
        return response["choices"][0]["message"]["content"]
import os
import openai

openai.api_key = os.getenv("OPENAI_API_KEY")

response = openai.ChatCompletion.create(
  model="gpt-3.5-turbo",
  messages=[
      {"role": "system", "content": "You will be provided with a color hex, and your task is to generate fun names for the colors."},
      {"role": "user", "content": "#FFFFFF"}
  ],
  temperature=0.8,
  max_tokens=256
)
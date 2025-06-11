AI Assistant Project

A cross-platform personal assistant built with Java, Python, and Svelte/Vue.js. Supports NLP, voice commands, reminders, and a sleek desktop UI via Tauri.

Tech Stack
- **Java**: Core logic, local SQLite storage
- **Python**: NLP (spaCy/OpenAI), voice (TTS/STT), MongoDB memory
- **Svelte/Vue**: User interface
- **SQLite**: Local structured data
- **MongoDB**: AI memory / chat history
- **Tauri**: Desktop packaging

Run Locally
```bash
# Backend (Java)
cd server
./gradlew run

# AI (Python)
cd ai
python main.py

# Frontend (Svelte/Vue)
cd client
npm run dev
```

Build Desktop App
```bash
cd client
tauri build
```

Screenshots
> Add demo screenshots or GIFs here

##License
MIT

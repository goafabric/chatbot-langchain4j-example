# k8sgpt auth add --backend ollama --model gpt-oss:20b --baseurl http://localhost:11434

k8sgpt auth add --backend ollama --model llama3.1:8b --baseurl http://localhost:11434

k8sgpt analyze --explain --backend ollama

k8sgpt serve --mcp --backend ollama
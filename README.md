# requirements
- needs a local llama2 that can be downloaded from https://ollama.ai/
- after download poke in "ollama run llama2", this will automatically download the smallest llama2 and open a chat console
- launch Application Class
- also has an example for remote chatgpt with the demo key

# links
https://github.com/langchain4j/langchain4j
https://blog.gopenai.com/langchain4j-supercharge-your-java-application-with-the-power-of-ai-6802e4237094
https://sych.io/blog/how-to-run-llama-2-locally-a-guide-to-running-your-own-chatgpt-like-large-language-model/

https://github.com/jmorganca/ollama/blob/main/docs/api.md

# ollama docker
docker run --rm -v ~/.ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama:0.1.13
docker exec -it ollama ollama run llama2

# curl
curl http://localhost:11434/api/generate -d '{
"model": "llama2",
"prompt": "Why is the sky blue?"
}'
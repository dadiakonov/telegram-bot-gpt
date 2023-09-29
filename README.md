# Telegram Bot Demo with OpenAI GPT Integration

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

## Overview

This application serves as a demonstration of a Telegram bot utilizing webhooks and integrating with the OpenAI API, specifically the GPT model. It's built with Spring Boot, Java, Gradle, and designed to run in Docker.

> **Note:** This is a demo project. For production use, it is recommended to replace the H2 database with a more robust solution and implement additional error handling and security features. The implementation provided should serve as a simple example rather than a production guideline.

## Features

- [TelegramWebhookHandler Interface](src/main/java/engineer/dima/buddy/telegram/webhook/TelegramWebhookHandler.java): Represents a handler for asynchronously processing Telegram webhook requests. Custom behavior for handling different types of Telegram webhook requests can be defined by implementing this interface.
- [MessageProcessingStrategy Interface](src/main/java/engineer/dima/buddy/message/MessageProcessingStrategy.java): Defines a strategy for processing messages based on user ID and message content, allowing for flexibility in message handling.
- [MessageContentFormatter Interface](src/main/java/engineer/dima/buddy/message/formatter/MessageContentFormatter.java): Strategy interface for formatting message content.

## Limitations

Due to Telegram's lack of support for streamed chunks of messages, there might be delays while waiting for responses from the OpenAI API. The bot responds to the webhook almost instantly, and the message processing and communication with external services happen asynchronously.

## Usage

### Prerequisites

1. **Register a Bot on Telegram:** Follow the instructions [here](https://core.telegram.org/bots#how-do-i-create-a-bot) to register a bot on Telegram.
2. **Create .env file:** Copy the `.env.example` file and create a `.env` file with the necessary configurations.

### Environment Variables

- `APP_TELEGRAM_BOT_TOKEN`: Telegram bot token.
- `APP_TELEGRAM_BOT_SECRET_TOKEN`: Secret token for requests from Telegram to your endpoint.
- `APP_TELEGRAM_BOT_WHITELISTED_USERS_ID`: List of user IDs allowed to use the bot.
- `APP_OPEN_AI_API_KEY`: OpenAI API Key.
- `APP_OPEN_AI_GPT_MODEL`: GPT model. More details [here](https://platform.openai.com/docs/models/overview).
- `APP_OPEN_AI_GPT_SYSTEM_MESSAGE`: Fundamental setting for bot behavior.
- `APP_OPEN_AI_GPT_CONTEXT_MESSAGES_QTY`: Maximum number of previous messages to send with a new request as GPT does not retain context in API communication.
- `APP_OPEN_AI_GPT_CONTEXT_LIMIT_IN_TOKENS`: Additional context limit to prevent excessive token usage.
- `APP_OPEN_AI_GPT_RESPONSE_LIMIT_IN_TOKENS`: Limit GPT responses by the number of tokens.

### Running the Application

1. **Build and Run Docker:**
    ```shell
    docker-compose up --build -d
    ```
2. **Create Webhook in Telegram:**
   ```shell
    curl -X "POST" "https://api.telegram.org/bot${APP_TELEGRAM_BOT_TOKEN}/setWebhook" \
    -d '{"url": "https://example.com/telegram/webhook", "allowed_updates": ["message"], "secret_token": "${APP_TELEGRAM_BOT_SECRET_TOKEN}"}' \
    -H 'Content-Type: application/json; charset=utf-8'
   ```
   > **Note:** Setup HTTPS for Webhook Endpoint. More details [here](https://core.telegram.org/bots/webhooks#the-short-version).

### Access Control

The bot is intended for private use, so specify each user ID in the `APP_TELEGRAM_BOT_WHITELISTED_USERS_ID` environment variable. For public usage, add the necessary entities and integrate with Spring Security.

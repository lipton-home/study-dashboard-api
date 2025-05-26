#!/bin/bash

echo 'localstack d'

brew install localstack -y

# shellcheck source=$HOME/.zshrc
source "$HOME/.zshrc"
source "$HOME/.bashrc"


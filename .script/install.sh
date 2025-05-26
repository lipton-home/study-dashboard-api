#!/bin/zsh

echo 'install localstack'

brew install localstack

echo 'install awscli-local'

brew install pipx
pipx install awscli-local
pipx ensurepath

source "$HOME/.zshrc"
#source "$HOME/.bashrc"

exit 0

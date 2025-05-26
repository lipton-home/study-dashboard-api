#!/bin/zsh


echo "setup awslocal profile"

PROFILE_NAME="localstack"

aws configure set aws_access_key_id test --profile $PROFILE_NAME
aws configure set aws_secret_access_key test --profile $PROFILE_NAME
aws configure set region us-east-1 --profile $PROFILE_NAME

echo 'alias awslocal="awslocal --profile localstack"' >> ~/.zshrc

source "$HOME/.zshrc"

exit 0
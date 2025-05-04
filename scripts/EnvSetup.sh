#!/bin/bash
# Environment setup for AI-CareerGuide project

# 1. Ensure Java JDK >= 8 is installed
if command -v java &> /dev/null
then
    echo "Java is installed"
    version=$(java -version 2>&1 | awk -F '"' '/version/ {print $2}')
    major_version=$(echo $version | cut -d '.' -f 1)
    minor_version=$(echo $version | cut -d '.' -f 2)
    if [ "$major_version" -gt 8 ] || { [ "$major_version" -eq 8 ] && [ "$minor_version" -ge 0 ]; }; then
        echo "Java version is greater than or equal to 8"
    else
        echo "Java version is less than 8. Please install a newer version."
        exit 1
    fi
else
    echo "Java is not installed. Installing OpenJDK 8..."
    sudo apt update
    sudo apt install -y openjdk-8-jdk
    if [ $? -eq 0 ]; then
        echo "Java OpenJDK 8 installed successfully."
    else
        echo "Failed to install Java OpenJDK 8."
        exit 1
    fi
fi

# 2. Ensure Python 3 and pip are installed
if ! command -v python3 &> /dev/null; then
    echo "Python3 not found. Installing..."
    sudo apt install -y python3
fi

if ! command -v pip3 &> /dev/null; then
    echo "pip3 not found. Installing..."
    sudo apt install -y python3-pip
fi

# 3. Install Python dependencies for Jupyter notebook and data generation
echo "Installing Python dependencies: numpy, matplotlib, noise, jupyter..."
pip3 install --user numpy matplotlib noise jupyter

# 4. (Optional) Install Jupyter Notebook if not present
if ! command -v jupyter &> /dev/null; then
    echo "Jupyter not found. Installing..."
    pip3 install --user notebook
fi

echo "Environment setup complete."

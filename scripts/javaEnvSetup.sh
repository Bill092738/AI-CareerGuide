# Ensure you have Java JDK greater than 8 this is for handling data processing which is done using Java

# Check if Java is installed
    # if installed
        # check version
            # if version is greater than 8
                # do nothing
            # else
                # install Java OpenJDK 8

if command -v java &> /dev/null
then
    echo "Java is installed"
    # Check Java version
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
    # Install Java OpenJDK 8
    sudo apt update
    sudo apt install -y openjdk-8-jdk
    if [ $? -eq 0 ]; then
        echo "Java OpenJDK 8 installed successfully."
    else
        echo "Failed to install Java OpenJDK 8."
        exit 1
    fi
fi


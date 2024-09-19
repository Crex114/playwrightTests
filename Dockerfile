# Используем базовый образ Playwright
#FROM mcr.microsoft.com/playwright:v1.45.1-jammy
FROM --platform=linux/amd64 mcr.microsoft.com/playwright:v1.45.1-jammy

# Установка Java (OpenJDK 11) и xvfb
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk xvfb && \
    java -version

# Определяем переменные для версии Gradle
ENV GRADLE_VERSION=7.6.1

# Установка Gradle
RUN apt-get update && \
    apt-get install -y wget unzip && \
    wget https://services.gradle.org/distributions/gradle-${GRADLE_VERSION}-bin.zip -P /tmp && \
    unzip /tmp/gradle-${GRADLE_VERSION}-bin.zip -d /opt && \
    ln -s /opt/gradle-${GRADLE_VERSION} /opt/gradle && \
    rm /tmp/gradle-${GRADLE_VERSION}-bin.zip

# Добавляем Gradle в PATH
ENV PATH="/opt/gradle/bin:${PATH}"

# Проверка версии Gradle
RUN gradle --version

# Установка Maven
ENV MAVEN_VERSION=3.9.5
RUN wget https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz -P /tmp && \
    tar -xzf /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
    ln -s /opt/apache-maven-${MAVEN_VERSION} /opt/maven && \
    rm /tmp/apache-maven-${MAVEN_VERSION}-bin.tar.gz

# Добавляем Maven в PATH
ENV PATH="/opt/maven/bin:${PATH}"

# Проверка версии Maven
RUN mvn --version

# Клонируем репозиторий с тестами Playwright
RUN git clone https://github.com/Crex114/playwrightTests.git

# Определяем рабочую директорию для проекта
WORKDIR /playwrightTests

# Открываем порты (если они необходимы для запуска)
EXPOSE 8090

# Команда по умолчанию при запуске контейнера
CMD ["/bin/bash"]

#  docker build -t my-playwright-gradle .
# docker run -p 8090:8090 -it --rm --ipc=host my-playwright-gradle
# mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chrome"

# docker cp 689fb076b109:/playwrightTests/build /Users/Evgen/Documents/ideaProjects/playwrightTests

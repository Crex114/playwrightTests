pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                // Проверка исходного кода из системы контроля версий (например, Git)
                git url: 'https://github.com/Crex114/playwrightTests.git', branch: 'main'
            }
        }

        stage('Install Playwright Browser') {
            steps {
                // Установка браузера Playwright с помощью Maven
                sh 'mvn exec -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install chrome"'
            }
        }

        stage('Build') {
            steps {
                // Компиляция проекта с использованием Gradle
                sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                // Запуск тестов с использованием Gradle и TestNG
                sh './gradlew test'
            }
        }

        stage('Allure Report') {
            steps {
                // Генерация отчётов Allure
                allure([
                    includeProperties: false,
                    jdk: '',
                    properties: [],
                    reportBuildPolicy: 'ALWAYS',
                    results: [[path: 'build/allure-results']]
                ])
            }
        }

        stage('Archive') {
            steps {
                // Архивация артефактов (например, jar-файлов)
                archiveArtifacts artifacts: '**/build/libs/*.jar', allowEmptyArchive: true
            }
        }
    }

    post {
        always {
            // Действия, которые будут выполнены независимо от результата сборки
            cleanWs() // Очистка рабочей области Jenkins
        }
        success {
            // Действия, которые будут выполнены только при успешной сборке
            echo 'Build succeeded!'
        }
        failure {
            // Действия, которые будут выполнены только при неудачной сборке
            echo 'Build failed!'
        }
    }
}

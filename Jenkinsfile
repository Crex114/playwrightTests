pipeline {
    agent any
    
    tools {
        // Указываем использовать установленный Maven
        maven 'maven jenkins'
        gradle 'gradle jenkins'
        allure 'allure jenkins'
    }
    
    stages {
//         stage('Checkout') {
//             steps {
//                 // Проверка исходного кода из системы контроля версий (например, Git)
//                 git url: 'https://github.com/Crex114/playwrightTests.git', branch: 'main'
//             }
//         }
        
//         stage('Install Chrome via Maven') {
//             steps {
//                 // Установка Chrome с помощью Maven
//                 sh 'mvn exec:java -e -D exec.mainClass=com.microsoft.playwright.CLI -D exec.args="install --with-deps chromium"'
//             }
//         }

        // stage('Ensure executable permissions for gradlew') {
        //     steps {
        //         // Выполнение команды для предоставления прав на выполнение gradlew
        //         sh 'chmod +x gradlew'
        //     }
        // }

        // stage('Install Playwright Browser') {
        //     steps {
        //         // Установка браузера Playwright с помощью Gradle // Исключаем тесты для отладки установки браузера
        //         sh './gradlew executeMavenCommand'
        //     }
        // }

        stage('Build') {
            steps {
                // Компиляция проекта с использованием Gradle
                sh 'chmod +x gradlew'
                sh './gradlew clean test'
            }
        }

        // stage('Test') {
        //     steps {
        //         // Запуск тестов с использованием Gradle и TestNG
        //         sh './gradlew test'
        //     }
        // }

        // stage('Allure Report') {
        //     steps {
        //         // Генерация отчётов Allure
        //         allure([
        //             includeProperties: false,
        //             jdk: '',
        //             properties: [],
        //             reportBuildPolicy: 'ALWAYS',
        //             results: [[path: 'build/allure-results']]
        //         ])
        //     }
        // }

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
            allure([
                includeProperties: false,
                jdk: '',
                properties: [],
                reportBuildPolicy: 'ALWAYS',
                results: [[path: 'build/allure-results']]
            ])
            
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

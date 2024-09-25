pipeline {
    agent any

    environment {
        IMAGE_NAME = 'my-playwright-gradle'
        CONTAINER_NAME = 'playwright_container'
        PORT_MAPPING = '8090:8090'
        GRADLE_WRAPPER = './gradlew'  // Путь к Gradle Wrapper, если используется
    }

    stages {
        stage('Clone Repository') {
            steps {
                script {
                    // Клонирование репозитория из Git
                    git url: 'https://github.com/Crex114/playwrightTests', branch: 'main' // Укажите URL и ветку
                    sh "ls -a"
                    sh "pwd"
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    // Сборка Docker-образа
                    docker.build(IMAGE_NAME, '.')
                }
            }
        }

        stage('Run Container') {
            steps {
                script {
                    // Запуск Docker-контейнера
                    docker.image(IMAGE_NAME).run("-p ${PORT_MAPPING} -it --rm --ipc=host --name ${CONTAINER_NAME}") {
                        sh "chmod +x gradle"
                        sh "gradle clean test"
                    }
                }
            }
        }
    }

    post {
        always {
            // Очистка после выполнения
            script {
                // Остановка и удаление контейнера, если он запущен
                sh "docker stop ${CONTAINER_NAME} || true"
                sh "docker rm ${CONTAINER_NAME} || true"
            }
        }
    }
}
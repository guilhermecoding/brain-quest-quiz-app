# BrainQuest: Quiz App Colaborativo com Firebase

## Visão Geral do Projeto

**BrainQuest** é um aplicativo de quiz moderno e funcional para Android, desenvolvido em Kotlin com **Jetpack Compose**. O projeto foca em uma experiência de usuário dinâmica, utilizando **Firebase** para funcionalidades robustas de autenticação e dados em tempo real.

O aplicativo permite que os usuários se cadastrem, participem de quizzes de diversas categorias, acompanhem seu progresso e compitam em um ranking global.

### Tecnologias Utilizadas:
* **Linguagem:** Kotlin
* **UI:** Jetpack Compose
* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Injeção de Dependência:** Hilt
* **Navegação:** Navigation Compose
* **Backend (Serviços de Nuvem):**
    * **Firebase Authentication:** Para gerenciamento de usuários.
    * **Cloud Firestore:** Armazenamento de dados em tempo real.
* **Persistência Local:**
    * **Room Database:** Histórico de quizzes.
    * **SharedPreferences:** Gerenciamento de sessão.
* **Assincronia:** Kotlin Coroutines & Flow

---

## Funcionalidades Implementadas

### a. Autenticação e Sessão
* **Cadastro e Login:** Sistema de autenticação seguro com e-mail e senha via **Firebase Authentication**.
* **Persistência de Sessão:** O aplicativo "lembra" do usuário, permitindo acesso direto à tela principal sem a necessidade de novo login.
* **Logout Seguro:** O usuário pode sair da conta a qualquer momento.

### b. Dashboard e Execução do Quiz
* **Tela Inicial Dinâmica:** Saúda o usuário pelo nome e pela hora do dia, exibindo o progresso de XP e uma lista de quizzes.
* **Carregamento de Quizzes da Nuvem:** Os quizzes são carregados em tempo real do **Cloud Firestore**, permitindo atualizações de conteúdo sem a necessidade de novas versões do app.
* **Execução do Quiz:** Interface interativa com timer, navegação entre perguntas e seleção de respostas em um layout intuitivo.
* **Controle de Progresso:** O botão "Próximo" é ativado somente após uma resposta ser selecionada, garantindo uma experiência de usuário fluida.

### c. Histórico e Ranking
* **Salvamento de Desempenho:** A pontuação de cada quiz é salva no **Cloud Firestore** (histórico global) e no **Room Database** (histórico local para acesso offline).
* **Atualização de Perfil:** A pontuação total (XP) e o recorde do usuário são atualizados em seu perfil no Firestore após cada quiz.
* **Tela de Ranking Global:** Exibe o Top 3 com medalhas (🥇, 🥈, 🥉) e a lista dos melhores jogadores, ordenada pela pontuação mais alta.
* **Histórico Pessoal:** A tela principal mostra as últimas partidas do usuário, com pontuação e data.

### d. Interface e Experiência do Usuário
* **Design Moderno:** UI desenvolvida com **Jetpack Compose** e seguindo as diretrizes do Material Design 3.
* **Animações:** A tela de resultados inclui uma animação Lottie para tornar a experiência mais recompensadora.
* **Responsividade:** O layout se adapta a diferentes tamanhos de tela.

---

## Arquitetura do Aplicativo
O projeto segue a arquitetura **MVVM**, recomendada pelo Google, para garantir um código limpo, escalável e fácil de testar.

* **View (UI Layer):** Camada de interface, responsável por exibir o estado e interagir com o ViewModel.
* **ViewModel (State Layer):** Contém a lógica de UI e o estado.
* **Repository (Data Layer):** Atua como uma única fonte de dados, gerenciando a busca em fontes remotas (Firestore) e locais (Room).

A injeção de dependência é gerenciada pelo **Hilt**, facilitando a criação de dependências e a testabilidade do código.

---

## APK para Testes
O arquivo de instalação do aplicativo (.apk) está disponível na seção **Releases** do repositório no GitHub para testes.

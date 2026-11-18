# 📅 Prazo Certo

> **Atividade desenvolvida durante aula na FIAP sobre Desenvolvimento Mobile em Kotlin com Firebase.**

O **Prazo Certo** é um aplicativo Android nativo desenvolvido para auxiliar estudantes no controle e acompanhamento de tarefas, prazos e entregas acadêmicas. O aplicativo combina uma interface moderna e declarativa em **Jetpack Compose** com a robustez dos serviços em nuvem do **Google Firebase** (Authentication e Realtime Database).

---

## 📱 Sobre o Projeto

Durante o período acadêmico, o gerenciamento de múltiplos prazos e disciplinas pode se tornar um desafio. O **Prazo Certo** foi concebido como uma solução móvel simples, intuitiva e centralizada para que o estudante possa:
- Criar e gerenciar sua conta de acesso com segurança ou acessar rapidamente com sua conta Google.
- Listar todas as atividades pendentes ou concluídas.
- Cadastrar novas tarefas indicando título, disciplina e data de entrega.
- Editar detalhes de prazos ou atualizar o status de entrega.
- Excluir tarefas finalizadas ou canceladas.

---

## 🛠️ Tecnologias e Ferramentas Utilizadas

### Core & Linguagem
- **Kotlin**: Linguagem oficial para desenvolvimento Android moderno.
- **Android SDK** (Compile SDK: 36, Target SDK: 35, Min SDK: 28).

### Interface & Design (UI)
- **Jetpack Compose**: Kit de ferramentas moderno e declarativo para construção de interfaces nativas.
- **Material 3 (Material Design)**: Design system com suporte a temas dinâmicos, paletas de cores customizadas, tipografia e componentes (Scaffold, TopAppBar, FloatingActionButton, LazyColumn, Dialogs).
- **Jetpack Navigation Compose**: Gerenciamento de rotas e fluxo de navegação entre telas (`login`, `signup` e `home`).

### Backend as a Service (BaaS)
- **Firebase Authentication**:
  - Autenticação por e-mail e senha.
  - Autenticação federada com **Google Sign-In** (`play-services-auth` + `GoogleAuthProvider`).
  - Gestão de sessão do usuário logado.
- **Firebase Realtime Database**:
  - Banco de dados NoSQL baseado em nuvem em formato JSON.
  - Operações completas de CRUD (criação, listagem, atualização e remoção de atividades).

### Boas Práticas & Segurança
- **BuildConfig & local.properties**: Injeção segura de variáveis sensíveis de ambiente (`DATABASE_URL` e `DEFAULT_WEB_CLIENT_ID`), evitando a exposição de credenciais no repositório público.
- **Estruturação por Camadas**: Divisão de código em `model`, `screens`, `ui.theme` e regras de build.

---

## 💡 O que foi aprendido e reforçado no projeto

Este projeto proporcionou uma imersão prática em conceitos essenciais da engenharia de software para dispositivos móveis:

1**Autenticação Híbrida e Segurança**:
   - Validação e cadastro de usuários via e-mail e senha no Firebase Auth.
   - Integração do fluxo OAuth 2.0 com Google via `rememberLauncherForActivityResult` e `ActivityResultContracts.StartActivityForResult`.
   - Troca de ID Token do Google por credenciais Firebase (`GoogleAuthProvider.getCredential`).
   - Encerramento completo de sessão unindo `FirebaseAuth.signOut()` e `GoogleSignInClient.signOut()`.

2**Operações NoSQL com Realtime Database**:
   - Estruturação de dados não relacionais em nós JSON.
   - Geração de identificadores únicos no banco com `push().key`.
   - Mapeamento objeto-relacional entre Kotlin Data Classes (`Activity`) e snapshots de dados do Firebase.
   - Atualização pontual (`updateChildren`) e deleção (`removeValue`).

3**Proteção de Segredos e Versionamento**:
   - Configuração de builds com `buildConfigField` lendo chaves locais no `local.properties`.
   - Configuração adequada de `.gitignore` para omitir chaves de API e arquivos gerados (`google-services.json`, diretórios de compilação).

---

## 📂 Estrutura do Projeto

```text
app/src/main/java/br/com/fiap/prazocerto/
├── MainActivity.kt           # Ponto de entrada e configuração do NavHost
├── model/
│   └── Activity.kt           # Modelo de dados da Atividade (POJO/Data Class)
├── screens/
│   ├── LoginScreen.kt        # Tela de login (E-mail/Senha e Google Sign-In)
│   ├── SignupScreen.kt       # Tela de criação de nova conta
│   ├── HomeScreen.kt         # Listagem de tarefas e ações de excluir/editar/logout
│   └── ActivityDialog.kt     # Diálogo de inserção e edição de atividades
└── ui/theme/
    ├── Color.kt              # Paleta de cores do app
    ├── Theme.kt              # Definição do tema Material3
    └── Type.kt               # Tipografia e estilos de texto
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Android Studio** (versão Ladybug / Jellyfish ou superior recomendada).
- **JDK 11** configurado.
- Dispositivo Android físico ou Emulador (Android 9.0 / API 28 ou superior).
- Projeto criado no console do **Firebase**.

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/gsabinou/prazocerto.git
   cd PrazoCerto
   ```

2. **Adicione o arquivo do Firebase:**
   - Faça o download do arquivo `google-services.json` no console do seu projeto Firebase.
   - Cole o arquivo na pasta `app/` do projeto.

3. **Configure as credenciais no `local.properties`:**
   No arquivo `local.properties` localizado na raiz do projeto, adicione as chaves:
   ```properties
   DATABASE_URL="https://SEU-PROJETO-DEFAULT-RTDB.firebaseio.com"
   DEFAULT_WEB_CLIENT_ID="SEU_ID_DO_CLIENTE_WEB_GOOGLE.apps.googleusercontent.com"
   ```

4. **Compile e execute:**
   - Abra o projeto no Android Studio.
   - Aguarde o sincronismo do Gradle.
   - Conecte um emulador ou dispositivo físico e clique em **Run 'app'** (`Shift + F10`).

---

## 🎓 Instituição

Projeto desenvolvido como parte dos estudos de **Desenvolvimento Mobile com Kotlin e Firebase** na **FIAP**.

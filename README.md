# 🧩 Projeto

## 🚀 Visão Geral

Este projeto é uma aplicação Java Spring Boot integrada com pipelines automatizados via GitHub
Actions.
O objetivo é fornecer uma base sólida para desenvolvimento, testes e deploy contínuo.

## 🏛️ Arquitetura do Sistema

O sistema segue uma arquitetura multicamadas organizada em Controller → Service → Repository,
utilizando também DTOs, Builder Pattern, Null Object Pattern, validações centralizadas e integração
externa via HTTP Client.

`Camada de Controlador (REST Controllers + Views)`

⬇

`Camada de Serviço (Regra de negócio + validação)`

⬇

`Camada de Repositório (Simulação de persistência)`

⬇

`Domain / Model (Produto, DTOs, Builder)`

```bash
Camada de Apresentação
  ├── ProdutoController
  ├── ProdutoViewController

Camada de Serviço
  ├── ProdutoServiceImpl
  └── ProdutoValidator

Camada de Repositório
  ├── ProdutoRepositoryImpl
  └── MockProduto

Camada de Domínio / Modelo
  ├── Produto, ProdutoReal, ProdutoNulo
  ├── ProdutoDto, ProdutoRequestDto, ProdutoResponsePayload
  └── ProdutoMapperImpl

Integração Externa (API)
  ├── Cotacao
  └── CotacaoPayload

Camada de Exceção
  ├── ProdutoNaoEncontradoException
  ├── EntradaInvalidaException
  └── ConversaoMoedaException
```

## 🏗️ Como Executar a Aplicação

### ✅ Pré-requisitos

- Java 21
- Maven 3.5.6
- Junit 5.13.4
- Spring Boot 3.5.6
- Mockito 5.20.0
- Selenium 4.36.0
- WebDriverManager 6.3.2
- JaCoCo 0.8.12

### 💻 Como executar a aplicação

#### 1. Clonar o repositório

```bash
git clone https://github.com/rodrigo-cloureiro/Rodrigo_Loureiro_PB_TP5
cd Rodrigo_Loureiro_PB_TP5
```

#### 2. Compilar e executar

```bash
mvn spring-boot:run
```

#### 3. Acessar a aplicação

```bash
http://localhost:8080
```

### 🧪 Como executar os testes

#### 1. Executar testes

```bash
mvn test
```

#### 2. Executar testes ignorando testes com Selenium

```bash
mvn test -Dgroups=!Selenium -B
```

### ▶️ Como executar e interpretar o workflow

O workflow é acionado automaticamente nos seguintes eventos:

| Evento              | Descrição                                      |
|---------------------|------------------------------------------------|
| `push`              | Quando há push no branch `main`                |
| `pull_request`      | Ao abrir ou atualizar PRs para o branch `main` |
| `workflow_dispatch` | Execução manual pelo GitHub                    |

### Workflow

#### Pipeline

[![Esteira CI/CD](https://github.com/rodrigo-cloureiro/Rodrigo_Loureiro_PB_TP5/actions/workflows/ci-cd.yml/badge.svg)](https://github.com/rodrigo-cloureiro/Rodrigo_Loureiro_PB_TP5/actions/workflows/ci-cd.yml)

#### Cobertura

[![codecov](https://codecov.io/github/rodrigo-cloureiro/Rodrigo_Loureiro_PB_TP5/graph/badge.svg?token=GJMWCSSVCR)](https://codecov.io/github/rodrigo-cloureiro/Rodrigo_Loureiro_PB_TP5)

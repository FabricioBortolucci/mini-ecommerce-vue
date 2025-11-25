# 🛒 Mini E-commerce Full Stack (Spring Boot + Vue.js)

![Java](https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Vue.js](https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D)
![Tailwind CSS](https://img.shields.io/badge/Tailwind_CSS-38B2AC?style=for-the-badge&logo=tailwind-css&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)


Este projeto é uma aplicação **Full Stack** robusta que simula um e-commerce completo. Ele foi desenvolvido com o objetivo de demonstrar a implementação de uma arquitetura moderna e segura, separando completamente o Backend (API REST) do Frontend (SPA), utilizando práticas profissionais de desenvolvimento como autenticação JWT, gerenciamento de estado global e deploy containerizado.

## 🚀 Demo Online

A aplicação está rodando em produção! Você pode testar todas as funcionalidades nos links abaixo:

🔗 **Acessar Aplicação:** [https://mini-ecommerce-vue.vercel.app/](https://mini-ecommerce-vue.vercel.app/)

### 🔑 Credenciais para Teste
Para testar as funcionalidades administrativas (CRUD de produtos), utilize:

| Perfil | Nome | Senha | Permissões |
| :--- | :--- | :--- | :--- |
| **Admin** | Clique em Demo | | Criar, Editar e Excluir Produtos |
| **Usuário** | (Registre um novo) | `123` | Comprar, Ver Meus Pedidos |

---

## 🛠️ Arquitetura e Tecnologias

O projeto segue a arquitetura de **Monorepo**, contendo tanto a API quanto o Cliente Web no mesmo repositório, mas com responsabilidades totalmente desacopladas.

### 🔙 Backend (API Rest)
Construído com **Java 17** e **Spring Boot 3**, focado em segurança e escalabilidade.
* **Spring Security 6:** Implementação de segurança stateless via tokens JWT (JSON Web Token).
* **JPA / Hibernate:** Camada de persistência com banco de dados PostgreSQL.
* **DTO Pattern:** Uso de *Records* para transferência de dados, evitando exposição direta das Entidades.
* **Role-Based Access Control (RBAC):** Controle de acesso granular (Admin vs User) nos endpoints.
* **Soft Delete:** Implementação de exclusão lógica para integridade referencial dos pedidos.

### 🖥️ Frontend (Single Page Application)
Construído com **Vue.js 3** (Composition API) e **Vite**, focado em reatividade e UX.
* **Pinia:** Gerenciamento de estado global para Carrinho de Compras, Autenticação e Notificações.
* **Vue Router:** Roteamento dinâmico com "Route Guards" para proteção de páginas administrativas.
* **Axios Interceptors:** Injeção automática do Token JWT em todas as requisições e monitoramento de tráfego.
* **Tailwind CSS:** Estilização responsiva e moderna.
* **System Monitor:** Um componente exclusivo desenvolvido para visualizar em tempo real as requisições HTTP e o status do sistema.

### ☁️ DevOps & Deploy
* **Docker:** Containerização da aplicação Java para deploy consistente.
* **Render:** Hospedagem do Backend e Banco de Dados PostgreSQL.
* **Vercel:** Hospedagem e CI/CD do Frontend.

---

## ✨ Funcionalidades Principais

### 🛍️ Experiência de Compra (Público/Usuário)
* **Vitrine de Produtos:** Listagem em grid com imagens e preços.
* **Detalhes do Produto:** Página dinâmica com informações detalhadas e dados do fornecedor.
* **Carrinho Inteligente:** Adição de itens, ajuste de quantidades e cálculo de subtotal em tempo real (via Pinia).
* **Checkout Seguro:** Finalização de compra exigindo autenticação.
* **Meus Pedidos:** Histórico de compras do usuário logado.

### 🔐 Segurança e Autenticação
* **Registro e Login:** Criação de conta e geração de Token JWT.
* **Proteção de Rotas:** Redirecionamento automático se o usuário tentar acessar áreas restritas sem estar logado ou sem permissão.
* **Persistência de Sessão:** O login é mantido mesmo ao recarregar a página (localStorage).

### ⚡ Painel Administrativo (Role: ADMIN)
* **Gestão de Produtos:** CRUD completo (Criar, Listar, Editar).
* **Exclusão Lógica:** Produtos "excluídos" são desativados para não quebrar o histórico de pedidos antigos, mas somem da loja.
* **Restauração:** Capacidade de reativar produtos desativados.

### 📡 Monitor de Sistema (Feature Exclusiva)
* Um terminal visual embutido na aplicação que mostra, em tempo real, as requisições HTTP saindo do frontend e as respostas do backend (Status 200, 403, 500), demonstrando a comunicação assíncrona.

---

## 🔧 Como Rodar Localmente

### Pré-requisitos
* Java 17+
* Node.js 18+
* PostgreSQL

### 1. Clone o Repositório
```bash
git clone [https://github.com/seu-usuario/seu-repositorio.git](https://github.com/seu-usuario/seu-repositorio.git)
cd seu-repositorio

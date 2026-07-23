# SISTEMA DE GERENCIAMENTO DE AGENDA DE COMPROMISSOS

## Sobre o Projeto

O Sistema de Gerenciamento de Agenda de Compromissos é uma aplicação desenvolvida em Java com o objetivo de permitir o controle e organização de diferentes tipos de compromissos.

O sistema permite cadastrar, consultar, remover, pesquisar e organizar compromissos como:

- Reuniões
- Eventos
- Tarefas

O projeto foi desenvolvido utilizando conceitos de Programação Orientada a Objetos (POO), explorando:

- Classes abstratas
- Herança
- Polimorfismo
- Encapsulamento
- Interfaces
- Collections
- Streams
- Comparable
- Tratamento de exceções
- Interface gráfica utilizando Java Swing


==================================================

# OBJETIVO DO PROJETO

O objetivo principal é criar um sistema capaz de organizar compromissos de maneira eficiente, permitindo que o usuário controle suas atividades futuras.

A aplicação busca aplicar boas práticas de desenvolvimento orientado a objetos, criando uma estrutura organizada, reutilizável e de fácil manutenção.


==================================================

# FUNCIONALIDADES

## Cadastro de compromissos

O sistema permite cadastrar três tipos de compromissos:


## 1. Reunião

Uma reunião representa um compromisso envolvendo uma pessoa específica.

Informações cadastradas:

- Data e horário
- Título
- Pessoa participante
- Descrição


Exemplo:

Tipo:
Reunião

Título:
Reunião do Projeto

Data:
25/07/2026 14:00

Pessoa:
Carlos

Descrição:
Definição das próximas tarefas


--------------------------------------------------


## 2. Evento

Um evento representa uma atividade com local definido.

Informações cadastradas:

- Data e horário
- Título
- Local
- Descrição


Exemplo:

Tipo:
Evento

Título:
Workshop Java

Data:
30/07/2026 19:00

Local:
Auditório

Descrição:
Evento acadêmico


--------------------------------------------------


## 3. Tarefa

Uma tarefa representa uma atividade que precisa ser realizada.

Informações cadastradas:

- Data
- Título
- Descrição
- Status de conclusão


Estados possíveis:

Pendente

ou

Concluída



==================================================

# INTERFACE GRÁFICA

A aplicação possui uma interface desenvolvida utilizando Java Swing.

A tela principal permite:

- Visualizar compromissos cadastrados
- Criar novos compromissos
- Excluir compromissos
- Atualizar informações


A tabela apresenta:

--------------------------------------------------
Campo          Descrição
--------------------------------------------------

ID             Identificador único

Tipo           Reunião, Evento ou Tarefa

Data/Hora      Data e horário do compromisso

Título         Nome do compromisso

Extra          Informação específica do tipo

--------------------------------------------------


Exemplo:

ID | Tipo | Data/Hora | Título | Extra

1 | Reunião | 25/07/2026 14:00 | Projeto Java | Carlos

2 | Evento | 30/07/2026 19:00 | Workshop | Auditório

3 | Tarefa | 02/08/2026 10:00 | Estudar POO | Pendente


==================================================

# ESTRUTURA DO PROJETO


src

|

|-- modelo

|   |

|   |-- Compromisso.java

|   |-- Evento.java

|   |-- Reuniao.java

|   |-- Tarefa.java

|

|-- servico

|   |

|   |-- Agenda.java

|

|-- ui

    |

    |-- Main.java



==================================================

# ORGANIZAÇÃO DOS PACOTES


## Pacote modelo

Responsável por representar os objetos do sistema.

Contém:

- Compromisso
- Reuniao
- Evento
- Tarefa


## Pacote servico

Responsável pelas regras de negócio.

Contém:

Agenda.java

Responsável por:

- Criar compromissos
- Armazenar informações
- Remover compromissos
- Pesquisar dados
- Validar horários
- Gerar relatórios


## Pacote ui

Responsável pela interface gráfica.

Contém:

Main.java

Responsável pela interação com o usuário.


==================================================
==================================================

# ARQUITETURA DO SISTEMA


O sistema foi desenvolvido seguindo uma divisão em camadas, separando responsabilidades entre interface, regras de negócio e modelos.


Estrutura:

              INTERFACE

                  |

                  ↓

              AGENDA

                  |

                  ↓

              MODELO



==================================================

# CAMADA MODELO


A camada modelo representa os objetos existentes no sistema.

A classe principal é:


Compromisso


Ela é uma classe abstrata que serve como base para os outros tipos de compromissos.


Estrutura de herança:


                 Compromisso

                       |

        --------------------------------

        |              |              |

     Reuniao        Evento        Tarefa



Essa estrutura utiliza o conceito de HERANÇA da Programação Orientada a Objetos.


==================================================

# CLASSE COMPROMISSO


Arquivo:

modelo/Compromisso.java


A classe Compromisso representa um compromisso genérico.

Ela é abstrata, portanto não pode ser criada diretamente.


Exemplo:


NÃO permitido:

Compromisso c = new Compromisso();


Permitido:


Reuniao r = new Reuniao();

Evento e = new Evento();

Tarefa t = new Tarefa();



==================================================

## ATRIBUTOS DA CLASSE COMPROMISSO


### ID


Representa o identificador único do compromisso.


O sistema gera automaticamente utilizando:


contadorId


Exemplo:


Reunião:

ID: 1


Evento:

ID: 2


Tarefa:

ID: 3



--------------------------------------------------


### DATA E HORA


Armazena quando o compromisso irá acontecer.


Utiliza:


LocalDateTime


Exemplo:


25/07/2026 14:30



--------------------------------------------------


### TÍTULO


Representa o nome do compromisso.


Exemplo:


"Reunião Projeto Java"



==================================================

# MÉTODOS DA CLASSE COMPROMISSO


## getId()


Retorna o identificador do compromisso.



## getDataHora()


Retorna a data e horário.



## getTitulo()


Retorna o título.



## getDataHoraFormatada()


Converte a data para o formato:


dd/MM/yyyy HH:mm


Exemplo:


25/07/2026 14:30



==================================================

# ORDENAÇÃO DOS COMPROMISSOS


A classe Compromisso implementa:


Comparable<Compromisso>



Isso permite comparar compromissos utilizando:


compareTo()



A comparação é realizada pela data e horário.



Exemplo:


Antes:


30/07/2026

20/07/2026

25/07/2026



Depois:


20/07/2026

25/07/2026

30/07/2026



==================================================

# CLASSE REUNIAO


Arquivo:


modelo/Reuniao.java



Representa reuniões entre pessoas.


Herança:


Reuniao extends Compromisso



Atributos:


- pessoa

- descricao



Exemplo:


Tipo:

Reunião


Título:

Planejamento do Projeto


Pessoa:

João


Descrição:

Definir próximas tarefas



Métodos:


getPessoa()

Retorna a pessoa da reunião.



getDescricao()

Retorna a descrição.



getTipo()

Retorna:


"Reunião"



detalhes()

Retorna informações adicionais:


Pessoa: João | Descrição: Definir tarefas



==================================================

# CLASSE EVENTO


Arquivo:


modelo/Evento.java



Representa eventos que possuem local definido.



Herança:


Evento extends Compromisso



Atributos:


- local

- descricao



Exemplo:


Tipo:

Evento


Título:

Workshop Java


Local:

Auditório


Descrição:

Evento acadêmico



Métodos:


getLocal()

Retorna o local do evento.



getDescricao()

Retorna a descrição.



getTipo()

Retorna:


"Evento"



detalhes()

Retorna:


Local: Auditório | Descrição: Evento acadêmico



==================================================

# CLASSE TAREFA


Arquivo:


modelo/Tarefa.java



Representa atividades que precisam ser realizadas.



Herança:


Tarefa extends Compromisso



Atributos:


- descricao

- concluida



A variável concluida controla o estado da tarefa.


Estados:


false:

Pendente


true:

Concluída



==================================================

# FUNCIONAMENTO DA TAREFA


Ao criar uma tarefa:


concluida = false



Resultado:


Status: Pendente



Quando o método:


concluir()


é chamado:


concluida = true



Resultado:


Status: Concluída



==================================================

# CAMADA SERVIÇO


## Classe Agenda


Arquivo:


servico/Agenda.java



A classe Agenda é responsável pelo funcionamento principal do sistema.


Ela controla:


- Cadastro

- Armazenamento

- Remoção

- Pesquisa

- Filtros

- Relatórios



Ela funciona como uma ponte entre:


Interface gráfica


e


Objetos do sistema



==================================================
==================================================

# ARMAZENAMENTO DOS COMPROMISSOS


A classe Agenda utiliza:


Set<Compromisso>


Implementado através de:


TreeSet



Código:


private final Set<Compromisso> compromissos;



==================================================

# POR QUE UTILIZAR TREESET?


O TreeSet foi escolhido porque possui duas características importantes:


## 1. ORDENAÇÃO AUTOMÁTICA


Os compromissos são organizados automaticamente utilizando o método:


compareTo()


da classe Compromisso.



Exemplo:


Cadastro:


30/07/2026 10:00

20/07/2026 15:00

25/07/2026 09:00



Após armazenamento:


20/07/2026 15:00

25/07/2026 09:00

30/07/2026 10:00



--------------------------------------------------


## 2. EVITA DUPLICIDADE


O sistema não permite dois compromissos no mesmo horário.



Exemplo:


Permitido:


25/07/2026 14:00

25/07/2026 15:00



Não permitido:


25/07/2026 14:00

25/07/2026 14:00



==================================================

# REGRAS DE NEGÓCIO


Antes de cadastrar qualquer compromisso, o sistema realiza validações.



## VALIDAÇÃO DE DATA


A data deve seguir o formato:


dd/MM/yyyy HH:mm



Exemplo válido:


25/07/2026 18:30



Exemplo inválido:


25-07-2026



--------------------------------------------------


## NÃO PERMITE DATAS PASSADAS


O sistema compara a data informada com a data atual.



Exemplo:


Data atual:

23/07/2026



Tentativa:


20/07/2026



Resultado:


Cadastro recusado.



--------------------------------------------------


## VERIFICAÇÃO DE HORÁRIO


Antes de cadastrar, o sistema verifica se o horário já está ocupado.



Método responsável:


horarioDisponivel()



Caso exista outro compromisso no mesmo horário:


Cadastro bloqueado.



==================================================

# PRINCIPAIS MÉTODOS DA CLASSE AGENDA


## adicionar()


Adiciona um compromisso já criado na agenda.



Retorno:


true

quando consegue adicionar.



false

quando ocorre algum problema.



--------------------------------------------------


## criarReuniao()


Responsável por criar reuniões.



Recebe:


- Data

- Título

- Pessoa

- Descrição



Retorna:


true se cadastrar com sucesso.



--------------------------------------------------


## criarEvento()


Responsável por criar eventos.



Recebe:


- Data

- Título

- Local

- Descrição



--------------------------------------------------


## criarTarefa()


Responsável por criar tarefas.



Recebe:


- Data

- Título

- Descrição



==================================================

# REMOÇÃO DE COMPROMISSOS


Método:


remover(int id)



O usuário informa o ID do compromisso.


O sistema procura o compromisso correspondente e remove.



Exemplo:


Antes:


ID 1 - Reunião

ID 2 - Evento

ID 3 - Tarefa



Removendo:


ID 2



Depois:


ID 1 - Reunião

ID 3 - Tarefa



==================================================

# SISTEMA DE PESQUISA


A agenda possui diversos filtros.



## Filtrar por dia


Método:


filtrarPorDia()



Busca compromissos de uma data específica.



Exemplo:


25/07/2026



--------------------------------------------------


## Filtrar por mês


Método:


filtrarPorMes()



Exemplo:


Julho de 2026



--------------------------------------------------


## Filtrar por ano


Método:


filtrarPorAno()



Exemplo:


Ano 2026



--------------------------------------------------


## Filtrar por título


Método:


filtrarPorTitulo()



Pesquisa palavras dentro do título.



Exemplo:


Busca:


Java



Resultados:


Reunião Java

Estudar Java



--------------------------------------------------


## Filtrar por pessoa


Método:


filtrarPorPessoa()



Pesquisa reuniões pelo nome da pessoa.



Exemplo:


Busca:


Carlos



Resultado:


Reunião com Carlos



==================================================

# GERAÇÃO DE RELATÓRIOS


O sistema possui geração de relatórios em formato texto.


Método:


gerarRelatorio()



Exemplo:


========== REUNIÕES ==========


[Reunião] ID:1 |

25/07/2026 14:00 |

Projeto Java |

Pessoa: João |

Descrição: Alinhamento


Total: 1 compromisso(s).



====================================



==================================================

# TECNOLOGIAS UTILIZADAS


## Linguagem


Java



## Interface gráfica


Java Swing



## Bibliotecas utilizadas


java.time

Manipulação de datas.



java.util

Coleções e estruturas de dados.



java.stream

Filtros e processamento de listas.



javax.swing

Construção da interface gráfica.



==================================================

# CONCEITOS DE PROGRAMAÇÃO APLICADOS


O projeto utiliza:


## Encapsulamento


Atributos privados protegidos por métodos getters.



## Herança


As classes:


Reuniao

Evento

Tarefa



herdam de:


Compromisso



## Polimorfismo


Cada tipo de compromisso implementa:


getTipo()


e


detalhes()



de maneira diferente.



## Classe abstrata


Compromisso define uma estrutura comum para todos os objetos.



## Comparable


Permite ordenar compromissos automaticamente.



## Collections


Utilização de:


TreeSet

List

ArrayList



## Streams


Utilizados para realizar filtros e pesquisas.



==================================================

# COMO EXECUTAR O PROJETO


## Requisitos


Necessário possuir:


- Java JDK 17 ou superior

- IDE Java (IntelliJ IDEA, Eclipse ou VS Code)



--------------------------------------------------


## Executando


1. Abra o projeto na IDE.


2. Compile os arquivos.


3. Execute:



Main.java



A interface gráfica será aberta.



==================================================

# POSSÍVEIS MELHORIAS FUTURAS


Algumas melhorias que podem ser implementadas:


- Persistência dos dados em banco de dados;

- Sistema de login de usuários;

- Exportação de relatórios em PDF;

- Calendário visual;

- Notificações de compromissos;

- Edição de compromissos existentes;

- Integração com Google Calendar;

- Salvamento em arquivos JSON;

- Interface gráfica mais moderna utilizando JavaFX.



==================================================

# CONCLUSÃO


O Sistema de Gerenciamento de Agenda de Compromissos demonstra a aplicação prática dos principais conceitos da Programação Orientada a Objetos em Java.


O projeto apresenta uma estrutura organizada, separação de responsabilidades e utilização de boas práticas de desenvolvimento, permitindo futuras expansões e melhorias.


==================================================

## Autores

José Francisco
Cesar 
Pedro Henrique
Everaldo

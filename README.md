# Assignment 3 - MVVM and Jetpack Compose
**Course:** Desenvolvimento de Aplicações Móveis (DAM)    
**Student(s):** Matilde Dias Gonçalves  
**Date:** 03/05/2026  
**Repository URL:** https://github.com/MattGon13/Cool-Weather-App-TP3.git

## 1. Introduction
Este exercício teve como principal objetivo a reconstrução da aplicação "CoolWeatherApp" realizada no trabalho prático anterior. Esta nova versão introduz uma transição completa para o padrão MVVM (Model-View-ViewModel, substituindo as tradicionais vistas baseadas em XML pelo *framework* declarativo Jetpack Compose.

## 2. System Overview
O sistema consiste numa aplicação móvel que apresenta os dados meteorológicos atuais com base nas coordenadas geográficas fornecidas (latitude e longitude). Através da interface, o utilizador pode visualizar métricas como a temperatura, velocidade do vento, direção do vento e pressão atmosférica ao nível do mar. Adicionalmente, o sistema integra funcionalidades novas, permitindo agora a escolha visual de locais através de um mapa interativo da Google e a capacidade de gravar locais recorrentes numa lista de favoritos dinâmica, para depois poderem ser selecionados novamente.

## 3. Architecture and Design
A aplicação foi desenvolvida seguindo a arquitetura recomendada pela Google, dividindo-se em três pacotes para garantir a separação de responsabilidades (Separation of Concerns):
* **`data`**: Encapsula a camada de dados. Aqui reside o `WeatherApiClient`, configurado com a biblioteca *Ktor* para tratar de forma assíncrona os pedidos de rede (HTTP) à API "open-meteo". Estão também presentes as classes de dados representativas, destacando-se a classe `Favourite` (que armazena o nome, latitude e longitude de um local gravado).
* **`viewmodel`**: Contém a classe `WeatherViewModel`, responsável por manter o estado da aplicação (`WeatherUIState`) utilizando `StateFlow`. Esta camada garante a integridade dos dados face a alterações de configuração da interface e coordena os pedidos meteorológicos, gerindo também em memória a lista de locais Favoritos.
* **`ui`**: Concentra a camada de apresentação exclusiva em Jetpack Compose. A interface observa e reage às mutações de estado através de componentes modulares como o `WeatherScreen.kt` e o `CoordinatesCard.kt`. Para suportar os desafios opcionais, foram introduzidos os módulos visuais `LocationPickerScreen.kt` (para renderização do mapa) e os blocos `FavouritesList` e `FavouritesSave` (para gestão da interface de favoritos).

## 4. Implementation
A implementação assentou na linguagem Kotlin com foco no padrão MVVM:
* A classe `MainActivity.kt` gere o ciclo de vida inicial, onde se valida as permissões de localização (Coarse/Fine Location) e se utiliza o `FusedLocationProviderClient` para capturar a posição real do utilizador.
* O `CoordinatesCard.kt` orquestra a lógica de *input* do utilizador. Incorpora um `rememberLauncherForActivityResult` para lançar um *Intent* direcionado à `LocationPickerActivity` e recuperar as novas coordenadas submetidas. Integra ainda um sistema de alternância visual (através da variável `addFavourite`) para mostrar a lista horizontal de favoritos (`LazyRow`) ou o formulário de gravação.
* A funcionalidade do mapa foi concretizada na `LocationPickerActivity`, que atua como contentor para o componente Compose `LocationPickerScreen`. Este ecrã recorre à biblioteca `Google Maps for Compose`, utilizando um `GoogleMap`, estado de câmara (`CameraPositionState`) e um `Marker` atualizável com base no evento `onMapClick`. Após a seleção do local, a atividade é terminada (`finish()`) devolvendo a latitude e longitude via *Intent Result*.

## 5. Testing and Validation
Os testes da aplicação concentraram-se na simulação das respostas em diferentes orientações de ecrã usando emuladores e dispositivos Android físicos. Foram testados ativamente:
* A recolha fiável da localização inicial via GPS.
* A formatação e leitura estruturada dos ficheiros JSON provenientes da *Open-Meteo API* via Ktor.
* A robustez da biblioteca de mapas, assegurando a renderização correta do mapa interativo e a precisão da captura do marcador geográfico submetido pelo utilizador.
* O fluxo bidirecional de *Intents*, garantindo que ao retornar da `LocationPickerActivity` o `CoordinatesCard` atualiza imediatamente os campos de texto e despacha um novo pedido meteorológico ao *ViewModel*.

## 6. Usage Instructions
1. Iniciar a aplicação utilizando um Emulador Android (AVD) ou um dispositivo físico com ligação à Internet.
2. Aprovar o acesso do sistema aos dados de Localização.
3. Modificar a localização inserindo novas coordenadas textuais nas caixas *Latitude* e *Longitude*.
4. Em alternativa, clicar no botão do globo terrestre para abrir o mapa em ecrã inteiro. Tocar no mapa para colocar um marcador na zona desejada e clicar no botão "Update" para confirmar a seleção.
5. Para guardar uma localização recorrente, clicar no símbolo de coração (ao lado do globo). Uma caixa de texto surgirá pedindo o nome do local. Preencher e carregar em "Add". O seu local passará a estar visível na forma de botões na base do cartão principal, permitindo trocas rápidas.

# Development Process

## 12. Version Control and Commit History
O Git foi utilizado para o controlo de versões. O histórico de commits reflete uma progressão contínua desde a configuração inicial (commit inicial) do projeto até à implementação das várias componentes do exercício, bem como melhorias feitas e erros corrigidos.
## 13. Difficulties and Lessons Learned
Dois grandes desafios sobressaíram neste projeto. O primeiro foi conceptual: compreender o paradigma de reatividade onde a *UI* deve apenas observar fluxos (`StateFlow`) exportados do ViewModel, rompendo os padrões imperativos do antigo *XML*. O segundo foi puramente técnico de navegação no Compose: a interligação de uma atividade Compose nativa com o sistema de partilha de *Intents* (utilizando o `rememberLauncherForActivityResult`) para capturar os retornos assíncronos das coordenadas do ecrã do Google Maps de uma forma fluída e isenta de falhas.

## 14. Future Improvements
Como extensão futura, a funcionalidade de Favoritos deveria utilizar persistência duradoura. Atualmente, os objetos do tipo `Favourite` são guardados em memória pela instância do ViewModel, reiniciando com um recomeço da App. A implementação de uma Base de Dados local (Android *Room Database*) seria o passo ideal para preservar permanentemente estas preferências.

## 15. AI Usage Disclosure (Mandatory)
De acordo com as regras estabelecidas no enunciado, não foi utilizada nenhuma ferramenta de IA para geração de código deste exercício.

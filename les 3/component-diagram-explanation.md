# Eenvoudig Component Diagram: Makkelijk Nieuwe Externe Services Toevoegen

## Ontwerpvraag
"Hoe zorg je ervoor dat je makkelijk een nieuwe externe service kan toevoegen?"

## Oplossingsoverzicht
Het component diagram toont een eenvoudig ontwerp dat het toevoegen van nieuwe externe services gemakkelijk maakt. Het gebruikt het C4 model om de oplossing te visualiseren.

## Ontwerpbeslissingen

### 1. Adapter Pattern
Elke externe service krijgt een eigen adapter die een gemeenschappelijke interface implementeert (`IExternalService`). Dit zorgt ervoor dat:
- De core applicatie niet hoeft te weten hoe elke specifieke externe service werkt
- Nieuwe services toegevoegd kunnen worden zonder de core code aan te passen
- Elke adapter de complexiteit van zijn externe API verbergt

In ons voorbeeld hebben we adapters voor:
- Transport service (voor het boeken van treinen, bussen, vluchten)
- Hotel service (voor het zoeken en boeken van accommodaties)
- Een nieuwe service (die laat zien hoe je extra services kunt toevoegen)

### 2. Service Gateway
De Service Gateway fungeert als een centrale toegangspoort voor alle externe services:
- Beheert welke service adapters beschikbaar zijn
- Routeert verzoeken naar de juiste adapter
- Kan fallback mechanismes bieden als een service uitvalt

### 3. Gemeenschappelijke Interface (IExternalService)
Alle service adapters implementeren dezelfde interface met standaard methoden:
- `connect()`: Verbinding maken met de externe service
- `executeRequest()`: Operaties uitvoeren op de service
- `handleError()`: Fouten op een standaard manier afhandelen
- `disconnect()`: Verbindingen netjes afsluiten

### 4. Duidelijke Grenzen
Het C4 diagram toont duidelijke grenzen tussen:
- Het overkoepelende TripTop systeem
- Externe systemen (Externe API's)

Deze grenzen maken verantwoordelijkheden helder en beperken de impact van wijzigingen.

### 5. Het Toevoegen van een Nieuwe Service

Om een nieuwe externe service toe te voegen, hoef je alleen maar:
1. Een nieuwe adapter klasse te maken die de `IExternalService` interface implementeert
2. De specifieke logica voor verbinding met de externe API te implementeren
3. De nieuwe adapter te registreren bij de Service Gateway

Deze aanpak volgt het **Open/Closed Principle** - de code is open voor uitbreiding (nieuwe adapters) maar gesloten voor wijziging (geen aanpassingen nodig aan bestaande code).

## Voordelen

- **Lage Koppeling**: De core applicatie is niet direct afhankelijk van externe services
- **Hoge Cohesie**: Elke adapter is verantwoordelijk voor één specifieke service
- **Eenvoudige Uitbreidbaarheid**: Nieuwe services toevoegen zonder bestaande code aan te passen
- **Testbaarheid**: Services kunnen gemakkelijk gemockt worden voor tests
- **Foutbestendigheid**: Centraal beheer van fouten en uitval van services
- **Duidelijke Visualisatie**: Het C4 model geeft een heldere weergave van de architectuur op componentniveau 
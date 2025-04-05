# Software Guidebook Triptop

## 1. Introduction
Dit software guidebook geeft een overzicht van de Triptop-applicatie. Het bevat een samenvatting van het volgende: 
1. De vereisten, beperkingen en principes. 
1. De software-architectuur, met inbegrip van de technologiekeuzes op hoog niveau en de structuur van de software. 
1. De ontwerp- en codebeslissingen die zijn genomen om de software te realiseren.
1. De architectuur van de infrastructuur en hoe de software kan worden geinstalleerd. 

## 2. Context

### context diagram

![context diagram](context-diagram/context-triptop.svg)
[View context diagram source](context-diagram/context-triptop.svg)


## 3. Functional Overview

Om de belangrijkste features toe te lichten zijn er user stories en twee domain stories gemaakt en een overzicht van het domein in de vorm van een domeinmodel. Op deze plek staat typisch een user story map maar die ontbreekt in dit voorbeeld.

### 3.1 User Stories

#### 3.1.1 User Story 1: Reis plannen

Als gebruiker wil ik een zelfstandig op basis van diverse variabelen (bouwstenen) een reis kunnen plannen op basis van mijn reisvoorkeuren (wel/niet duurzaam reizen, budget/prijsklasse, 's nachts reizen of overdag etc.) zodat ik op vakantie kan gaan zonder dat hiervoor een reisbureau benodigd is.

#### 3.1.2 User Story 2: Reis boeken

Als gebruiker wil ik een geplande reis als geheel of per variabele (bouwsteen) boeken en betalen zodat ik op vakantie kan gaan zonder dat hiervoor een reisbureau benodigd is.

#### 3.1.3 User Story 3: Reis cancelen

Als gebruiker wil ik een geboekte reis, of delen daarvan, kunnen annuleren zodat ik mijn geld terug kan krijgen zonder inmenging van een intermediair zoals een reisbureau.

#### 3.1.4 User Story 4: Reisstatus bewaren 

Als gebruiker wil ik mijn reisstatus kunnen bewaren zonder dat ik een extra account hoef aan te maken zodat ik mijn reis kan volgen zonder dat ik daarvoor extra handelingen moet verrichten.

#### 3.1.5 User Story 5: Bouwstenen flexibel uitbreiden

Als gebruiker wil ik de bouwstenen van mijn reis flexibel kunnen uitbreiden met een zelf te managen stap (bijv. met providers die niet standaard worden aangeboden zoals een andere reisorganisatie, hotelketen etc.) zodat ik mijn reis helemaal kan aanpassen aan mijn wensen.

### 3.2 Domain Story Reis Boeken (AS IS)

![Domain Story Reis Boeken AS IS](../opdracht-diagrammen/reis-boeken-asis-coursegrained_2024-06-11.egn.svg)

### 3.3 Domain Story Reis Boeken (TO BE)

![Domain Story Reis Boeken TO BE](../opdracht-diagrammen/reis-boeken-tobe-coursegrained_2024-06-11.egn.svg)

### 3.4 Domain Model

![Domain Model](../opdracht-diagrammen/Domain%20Model.png)

| class: attribute | is input voor API+endpoint | wordt gevuld door API+endpoint | wordt geleverd door eindgebruiker | moet worden opgeslagen in de applicatie |
|------------------|----------------------------|--------------------------------|-----------------------------------|-----------------------------------------|
| Trip::startDatum | Booking /search (POST), FlightScraper /flights (GET) |  | x | x |
| Trip::eindDatum | Booking /search (POST), FlightScraper /flights (GET) |  | x | x |
| Trip::budget |  |  | x | x |
| Excursie::titel |  | TripAdvisor /search |  | x |
| Excursie::datum |  | TripAdvisor /search |  | x |
| Excursie::startTijd |  | TripAdvisor /search |  | x |
| Excursie::eindTijd |  | TripAdvisor /search |  | x |
| Excursie::prijs |  | TripAdvisor /search |  | x |
| Reis::startDatum | FlightScraper /flights (GET) |  | x | x |
| Reis::eindDatum | FlightScraper /flights (GET) |  | x | x |
| Reis::prijs |  | FlightScraper /flights (GET) |  | x |
| Reis::vervoer |  |  | x | x |
| Verblijf::startDatum | Booking /search (POST) |  | x | x |
| Verblijf::eindDatum | Booking /search (POST) |  | x | x |
| Verblijfplaats::locatie |  | Booking /search (POST) |  | x |
| Verblijfplaats::prijs |  | Booking /search (POST) |  | x |
| Locatie::lat |  | Booking /search (POST), TripAdvisor /search |  | x |
| Locatie::lon |  | Booking /search (POST), TripAdvisor /search |  | x |
| Reiziger::voornaam |  |  | x | x |
| Reiziger::achternaam |  |  | x | x |
| Reiziger::telefoonnummer |  |  | x | x |
| Reservering::reserveringsnummer |  | Booking /confirm (POST), FlightScraper /book (POST) |  | x |
| Reservering::status |  | Booking /status (GET), FlightScraper /status (GET) |  | x |

## 4. Quality Attributes

Voordat deze casusomschrijving tot stand kwam, heeft de opdrachtgever de volgende ISO 25010 kwaliteitsattributen benoemd als belangrijk:
* Compatibility -> Interoperability (Degree to which a system, product or component can exchange information with other products and mutually use the information that has been exchanged)
* Reliability -> Fault Tolerance (Degree to which a system or component operates as intended despite the presence of hardware or software faults)
* Maintainability -> Modularity (Degree to which a system or computer program is composed of discrete components such that a change to one component has minimal impact on other components)
* Maintainability -> Modifiability (Degree to which a product or system can be effectively and efficiently modified without introducing defects or degrading existing product quality)
* Security -> Integrity (Degree to which a system, product or component ensures that the state of its system and data are protected from unauthorized modification or deletion either by malicious action or computer error)
* Security -> Confidentiality (Degree to which a system, product or component ensures that data are accessible only to those authorized to have access)

## 5. Constraints

> [!IMPORTANT]
> Beschrijf zelf de beperkingen die op voorhand bekend zijn die invloed hebben op keuzes die wel of niet gemaakt kunnen of mogen worden.

## 6. Principles


In softwareontwikkeling is het belangrijk om een duidelijke en gestructureerde codebase te behouden. Dit zorgt niet alleen voor betere onderhoudbaarheid en schaalbaarheid, maar ook voor een efficiëntere samenwerking binnen het team. In dit hoofdstuk bespreken we twee fundamentele principes die bijdragen aan goed gestructureerde software: het Single Responsibility Principle (SRP) en Separation of Concerns (SoC). Door deze principes toe te passen, zorgen we ervoor dat onze code overzichtelijk, flexibel en robuust blijft.

### Single Responsibility Principle (SRP)

Het Single Responsibility Principle stelt dat een klasse slechts één duidelijke verantwoordelijkheid moet hebben. Dit betekent dat elke klasse slechts één reden mag hebben om te veranderen. Door dit principe toe te passen, voorkomen we dat een klasse te veel verschillende taken uitvoert, wat de code complex en moeilijk te onderhouden maakt.

Voorbeelden van SRP in onze codebase:

HotelService: Verantwoordelijk voor alle hotelgerelateerde operaties, zoals het ophalen van hotelgegevens en het verwerken van reserveringen.

TransportService: Verantwoordelijk voor transportgerelateerde operaties, zoals het regelen van vervoer voor een reis.

ApiConfig: Verantwoordelijk voor de configuratie van de API, zoals authenticatie en verbindingen met externe services.

TripController: Verantwoordelijk voor het afhandelen van HTTP-verzoeken met betrekking tot reizen.

Door deze scheiding zorgen we ervoor dat elke klasse een duidelijke focus heeft, wat leidt tot betere testbaarheid en herbruikbaarheid van code.

### Separation of Concerns (SoC)

Het Separation of Concerns-principe benadrukt het belang van het opdelen van software in aparte onderdelen met elk een eigen verantwoordelijkheid. Dit voorkomt dat verschillende lagen van de applicatie in elkaar overlopen, wat kan leiden tot een onoverzichtelijke codebase.

Een duidelijke scheiding in onze architectuur:

API-configuratie: Wordt beheerd door ApiConfig, waarin alle instellingen en connecties met externe systemen worden vastgelegd.

Business logic: Behandeld door services zoals HotelService en TransportService, waarin de kernfunctionaliteit van de applicatie wordt geïmplementeerd.

HTTP endpoints: Gehandeld door controllers, zoals TripController, die verantwoordelijk is voor het verwerken van inkomende verzoeken en het aanroepen van de juiste services.

Data modellen: Worden gebruikt om de structuur van gegevens te definiëren en om dataoverdracht binnen de applicatie mogelijk te maken.

Door Separation of Concerns strikt toe te passen, zorgen we ervoor dat wijzigingen in één onderdeel van de applicatie minimale impact hebben op de rest van het systeem. Dit maakt het makkelijker om nieuwe functionaliteiten toe te voegen en bestaande code te onderhouden.

### Open/Closed Principle (OCP)

Het Open/Closed Principle stelt dat software-entiteiten (klassen, modules, functies, etc.) open moeten staan voor uitbreiding, maar gesloten voor modificatie. Dit betekent dat we bestaande code niet moeten wijzigen om nieuwe functionaliteit toe te voegen, maar in plaats daarvan nieuwe code moeten toevoegen die de bestaande code uitbreidt.

Voorbeelden van OCP in onze codebase:

Adapter Interfaces: De `HotelAdapter` en `TransportAdapter` interfaces zijn open voor uitbreiding door nieuwe implementaties toe te voegen, zonder dat de bestaande code hoeft te worden gewijzigd.

Service Interfaces: De `IExternalService` interface maakt het mogelijk om nieuwe services toe te voegen zonder de bestaande service-implementaties te wijzigen.

Controller Endpoints: Onze controllers zijn ontworpen om nieuwe endpoints toe te voegen zonder bestaande endpoints te wijzigen.

Door het Open/Closed Principle toe te passen, zorgen we ervoor dat onze codebase flexibel en uitbreidbaar blijft, terwijl we het risico op het introduceren van bugs in bestaande functionaliteit minimaliseren. Dit maakt het mogelijk om nieuwe features toe te voegen zonder de stabiliteit van het systeem in gevaar te brengen.

## 7. Software Architecture

###     7.1. Containers

![Container Diagram](container-diagram/container-triptop.svg)
[View container diagram source](container-diagram/container-triptop.svg)

###     7.2. Components

![Database Component Diagram](component-diagram/database-component-diagram-triptop.svg)
[View database component diagram source](component-diagram/database-component-diagram-triptop.svg)

![Backend Component Diagram](component-diagram/backend-component-diagram-triptop.svg)
[View backend component diagram source](component-diagram/backend-component-diagram-triptop.svg)

![Frontend Component Diagram](component-diagram/frontend-component-diagram-triptop.svg)
[View frontend component diagram source](component-diagram/frontend-component-diagram-triptop.svg)

###     7.3. Design & Code

### facade 

Ontwerpvraag: "Wie roept een specifieke externe service aan, gebeurt dat vanuit de front-end of vanuit de back-end? Welke redenen zijn er om voor de ene of de andere aanpak te kiezen?"

#### class diagram
![facade class Diagram](class-diagram/facade-class-diagram.svg)

Het Facade patroon is geïmplementeerd om een vereenvoudigde interface te bieden voor een complex subsysteem van services. In dit diagram:
- `TripFacade` fungeert als de hoofdinterface voor clients, waarbij de complexiteit van de onderliggende services wordt verborgen
- `HotelService`, `TransportService` en `ExcursionService` zijn de subsysteemcomponenten die specifieke aspecten van reisplanning afhandelen
- De facade coördineert deze services en biedt een uniforme interface voor reisgerelateerde operaties
- Dit patroon helpt de koppeling tussen clients en het subsysteem te verminderen, waardoor het systeem beter onderhoudbaar en gebruiksvriendelijker wordt

#### sequence diagram
![facade sequence Diagram](sequence-diagram/facade.sequence.diagram.svg)

Het sequentiediagram illustreert hoe het Facade patroon een reisplanning verzoek afhandelt:
1. De client communiceert alleen met de `TripFacade`, die fungeert als enig contactpunt
2. De facade coördineert de benodigde operaties over meerdere services
3. Elke service (`HotelService`, `TransportService`, `ExcursionService`) handelt zijn specifieke domein af
4. De facade combineert de resultaten en retourneert een uniform antwoord aan de client
5. Deze flow toont aan hoe de facade complexe interacties vereenvoudigt terwijl de scheiding van verantwoordelijkheden behouden blijft

### Adapter
#### class diagram
![adapter class Diagram](class-diagram/adapter-class-diagram.svg)

Het Adapter patroon is geïmplementeerd om incompatibele interfaces tussen ons systeem en externe services te overbruggen:
- `IExternalService` definieert de interface die ons systeem verwacht
- `HotelAdapter` en `TransportAdapter` implementeren deze interface en passen de interfaces van externe services aan
- De adapters vertalen verzoeken en antwoorden tussen het formaat van ons systeem en de formaten van externe services
- Dit patroon stelt ons in staat om te integreren met verschillende externe services zonder ons kernsysteem te wijzigen

#### sequence diagram
![adapter sequence Diagram](sequence-diagram/adapter-sequence.diagram.svg)

Het sequentiediagram toont hoe het Adapter patroon de communicatie met externe services afhandelt:
1. De client doet een verzoek via de interface van ons systeem
2. De adapter ontvangt het verzoek en vertaalt het naar het formaat dat de externe service verwacht
3. De externe service verwerkt het verzoek en retourneert een antwoord
4. De adapter vertaalt het antwoord terug naar het formaat van ons systeem
5. Dit proces zorgt voor naadloze integratie tussen ons systeem en externe services met verschillende interfaces

### factory

![factory class Diagram](class-diagram/factory-class-diagram.svg)

> [!IMPORTANT]
> Voeg toe: Per ontwerpvraag een Class Diagram plus een Sequence Diagram van een aantal scenario's inclusief begeleidende tekst.

## 8. Architectural Decision Records

### 8.1. ADR-001 Integration with TripAdvisor API for Accommodation Search

Date: 2024-03-21

## Status

Accepted

## Context

De Triptop-applicatie moet accommodatiezoekfunctionaliteit bieden zodat gebruikers hotels kunnen vinden en boeken als onderdeel van hun reisplanning. In plaats van onze eigen accommodatiedatabase te bouwen en te onderhouden, moeten we integreren met een bestaande service die uitgebreide en actuele accommodatiegegevens biedt.

Enkele belangrijke vereisten zijn toegang tot een grote database van accommodaties wereldwijd, de mogelijkheid om te zoeken op locatie, data en andere filters, en gedetailleerde informatie over elke accommodatie, inclusief foto's, prijzen en recensies. Daarnaast is ondersteuning voor verschillende talen en valuta's nodig.

## Considered Options

| Force | TripAdvisor API | Expedia API | Hotels.com API | Airbnb API |
|-------|----------------|-------------|---------------|------------|
| **Data Completeness** | ++ | + | + | 0 |
| **Global Coverage** | ++ | + | + | 0 |
| **Development Effort** | + | 0 | 0 | - |
| **Integration Complexity** | + | 0 | 0 | - |
| **Maintenance Burden** | + | + | + | + |
| **Update Frequency** | ++ | + | + | + |
| **API Stability** | + | 0 | 0 | - |
| **Variety of Accommodations** | + | + | 0 | ++ |
| **Documentation Quality** | ++ | + | 0 | - |
| **Multi-language Support** | ++ | + | + | 0 |

Legend:
- ++ : Excellent fit / Strong advantage
- + : Good fit / Advantage
- 0 : Neutral / Average
- - : Poor fit / Disadvantage
- -- : Very poor fit / Strong disadvantage

## Decision

We zullen integreren met de TripAdvisor API via RapidAPI om accommodatiezoekfunctionaliteit te bieden. De integratie zal bestaan uit:

1. Een client-side wrapper (`BookingApiClient`) die API-verzoeken en responsemapping afhandelt
2. Uitbreiding van ons domeinmodel om aan de TripAdvisor API-vereisten te voldoen
3. Mapping tussen ons domeinmodel en de TripAdvisor API-datastructuren

## Consequences

### Positive
Toegang tot een uitgebreide, actuele database van accommodaties wereldwijd is een voordeel. De gegevens worden professioneel beheerd met regelmatige updates. Er is ook minder ontwikkelings- en onderhoudsinspanning in vergelijking met het onderhouden van onze eigen accommodatiedatabase. Bovendien profiteert de applicatie van een gestandaardiseerde interface voor accommodatiezoeken in verschillende delen van het systeem.

### Negative
Er is echter een afhankelijkheid van een externe service, die kan veranderen of onbeschikbaar kan worden. Mogelijke kosten verbonden aan API-gebruik kunnen ontstaan naarmate de applicatie schaalt. Het domeinmodel zou ook moeten worden aangepast aan de API-vereisten. Ten slotte is er beperkte controle over de beschikbare gegevens en functies.

### 8.2. ADR-002 Use of Two Databases for Security Reasons

## Status
Accepted

## Context
De reisapplicatie moet gevoelige gegevens verwerken, inclusief persoonlijke informatie, betalingsgegevens en reisroutes. Om de beveiliging te verbeteren en het risico bij een inbreuk te minimaliseren, overwegen we het gebruik van twee aparte databases. Eén zal algemene applicatiegegevens opslaan en de andere zal gevoelige gegevens opslaan. Deze aanpak is gericht op het isoleren van de gevoelige gegevens om een extra beveiligingslaag toe te voegen.

## Considered Options

| Option                     | Single Database | Two Separate Databases |
| -------------------------- | --------------- | ---------------------- |
| **Operational Complexity** | +               | -                      |
| **Security**               | -               | ++                     |
| **Scalability**            | +               | +                      |
| **Maintainability**        | 0               | -                      |

Legend:
- ++ : Excellent fit / Strong advantage
- + : Good fit / Advantage
- 0 : Neutral / Average
- - : Poor fit / Disadvantage
- -- : Very poor fit / Strong disadvantage

## Decision
We zullen twee databases implementeren: 
De eerste database zal niet-gevoelige gebruikersgegevens opslaan, zoals gebruikersvoorkeuren, algemene reisinformatie en niet-gevoelige logs. De tweede database zal gevoelige informatie opslaan zoals persoonlijke gegevens, betalingsgegevens, reisdocumenten en andere hoogrisicogegevens. Door deze twee soorten gegevens te scheiden, kunnen we meer gedetailleerde beveiligingscontroles implementeren op de gevoelige database, de algehele beveiligingspositie van de applicatie verbeteren door de toegang tot gevoelige gegevens te beperken, en strengere encryptiebeleid toepassen voor de gevoelige database.

Voor de gevoelige database zal encryptie in rust worden afgedwongen met behulp van sterke encryptie-algoritmen, zal toegangscontrole nauwkeurig worden afgesteld en zal gegevensmaskering worden toegepast waar nodig. Voor de niet-gevoelige database zal de encryptie minder streng zijn, maar zal de toegang nog steeds beperkt zijn.

## Consequences
Het beheren van twee databases zal extra operationele complexiteit introduceren, inclusief databasesynchronisatie, backupbeheer en onderhoudsoverhead. Er kunnen ook prestatie-effecten zijn door de noodzaak om gegevens over twee databases te benaderen, wat latentie kan introduceren. Met zorgvuldig ontwerp en optimalisatie kunnen deze effecten echter worden geminimaliseerd.

Door gevoelige gegevens te isoleren in een aparte database, minimaliseren we het risico op blootstelling in geval van een database-inbreuk. Als een aanvaller de niet-gevoelige database compromitteert, hebben ze nog steeds geen toegang tot de gevoelige gegevens. Het onderhouden van twee databases kan echter extra kosten met zich meebrengen gerelateerd aan infrastructuur, backup en gegevenssynchronisatie.

## Alternatives Considered
Een overwogen alternatief was het gebruik van een enkele database voor alle gegevens, met strengere toegangscontrole en encryptie. Hoewel dit de architectuur zou vereenvoudigen, zou het alle gegevens blootstellen aan hogere risico's als de database wordt gecompromitteerd. Een ander alternatief was databasesharding, waarbij meerdere shards zouden worden gebruikt voor schaalbaarheid terwijl alle gegevens in dezelfde database blijven. Dit zou echter niet hetzelfde niveau van isolatie en beveiliging bieden voor gevoelige gegevens als twee aparte databases.

## Conclusion
Gezien de behoefte aan verhoogde beveiliging, is het isoleren van gevoelige gegevens in een aparte database de beste optie. Deze aanpak maakt meer gecontroleerde toegang, verhoogde encryptie en een sterker algeheel beveiligingsmodel mogelijk, terwijl de toegevoegde complexiteit en prestatieoverwegingen in balans worden gehouden.

### 8.3. ADR-003: State Management

## Status
Accepted

## Context
De TripTop-applicatie vereist robuust state management om complexe gebruikersinteracties, boekingsprocessen en real-time updates over zowel web- als mobiele platforms te kunnen verwerken. We hebben een oplossing nodig die efficiënt de applicatiestatus kan beheren, neveneffecten kan afhandelen en consistentie kan behouden over verschillende componenten.

## Considered Options

| Option                     | Redux | Context API | MobX |
| -------------------------- | ----- | ----------- | ---- |
| **Scalability**            | ++    | +           | +    |
| **Learning Curve**         | -     | ++          | -    |
| **Developer Experience**   | +     | +           | +    |
| **Community Support**      | ++    | +           | +    |
| **Integration**            | ++    | +           | +    |

Legend:
- ++ : Excellent fit / Strong advantage
- + : Good fit / Advantage
- 0 : Neutral / Average
- - : Poor fit / Disadvantage
- -- : Very poor fit / Strong disadvantage

## Decision
We zullen Redux implementeren als onze state management-oplossing voor zowel de web- als mobiele applicaties. Redux biedt een voorspelbare state container die ons zal helpen de status van de applicatie in een gecentraliseerde store te beheren. Dit omvat het beheren van authenticatiestatus, boekingsinformatie, reisplanninggegevens en betalingsverwerkingsstatussen.

De beslissing om Redux te gebruiken is gebaseerd op verschillende belangrijke factoren. Redux biedt gecentraliseerd state management dat ons in staat stelt een enkele bron van waarheid te behouden voor onze applicatiestatus. De voorspelbare statusupdates via reducers zorgen ervoor dat statuswijzigingen consistent worden afgehandeld en gemakkelijk te debuggen zijn. Het sterke ecosysteem en community support betekent dat we toegang hebben tot een schat aan bronnen, middleware en tools. Redux's uitstekende integratie met React en React Native maakt het een natuurlijke keuze voor onze tech stack, terwijl de ingebouwde ontwikkelaarstools krachtige debugmogelijkheden bieden.

## Consequences
Het gebruik van Redux zal verschillende significante voordelen bieden aan onze applicatie. De voorspelbare statusupdates en debugmogelijkheden zullen het gemakkelijker maken om problemen in onze applicatie op te sporen en op te lossen. Het gecentraliseerde state management over componenten zal helpen consistentie te behouden en de complexiteit van statussynchronisatie te verminderen. Deze aanpak zal leiden tot betere codeorganisatie en onderhoudbaarheid, wat het voor ons team gemakkelijker maakt om aan de codebase te werken. De verbeterde ontwikkelaarservaring met Redux DevTools zal onze ontwikkelworkflow verder verbeteren.

Het implementeren van Redux introduceert echter ook bepaalde uitdagingen. De extra boilerplate-code die nodig is voor actions en reducers kan eenvoudige statuswijzigingen onnodig complex maken. Nieuwe ontwikkelaars zullen Redux's concepten en patronen moeten leren, wat de initiële ontwikkeling kan vertragen. Er is ook het risico van over-engineering voor eenvoudige statuswijzigingen, en we zullen onze statusstructuur zorgvuldig moeten plannen om onnodige complexiteit te voorkomen.

## Alternatives Considered
We hebben verschillende alternatieven zorgvuldig geëvalueerd voordat we voor Redux kozen. De React Context API werd overwogen vanwege zijn eenvoud en ingebouwde aard, maar mist de robuuste state management-functies die nodig zijn voor onze complexe applicatie. MobX was een andere optie die goede state management-mogelijkheden biedt, maar heeft een steilere leercurve en minder community support in vergelijking met Redux.

## Conclusion
Redux is de optimale keuze voor onze state management-behoeften, die de juiste balans biedt tussen functies, community support en integratiemogelijkheden voor zowel onze web- als mobiele applicaties. Hoewel het enige complexiteit introduceert, wegen de voordelen van voorspelbaar state management, sterk ecosysteem support en uitstekende ontwikkelaarstools op tegen de initiële leercurve en extra boilerplate-code.

### 8.4. ADR-004: Database Type

## Status
Accepted

## Context
De TripTop-applicatie vereist een flexibele en schaalbare database-oplossing die verschillende soorten gegevens kan verwerken, inclusief gebruikersprofielen, reisinformatie, boekingen en authenticatiegegevens. De datastructuur kan in de loop van de tijd evolueren naarmate we nieuwe functies en vereisten toevoegen. We hebben een database nodig die zowel gestructureerde als semi-gestructureerde gegevens efficiënt kan verwerken terwijl goede prestaties en schaalbaarheid worden behouden.

## Considered Options

| Option                     | MongoDB   | PostgreSQL | MySQL | CouchDB  |
| -------------------------- |-----------| ---------- | ----- |----------|
| **Schema Flexibility**     | ++        | -          | -     | ++       |
| **Scalability**            | ++        | +          | +     | +        |
| **Query Performance**      | +         | ++         | +     | -        |
| **Data Consistency**       | -         | ++         | ++    | -        |
| **Development Speed**      | ++        | +          | +     | +        |
| **Offline Support**        | -         | -          | -     | ++       |
| **Community Size**         | ++        | ++         | ++    | -        |

Legend:
- ++ : Excellent fit / Strong advantage
- + : Good fit / Advantage
- 0 : Neutral / Average
- - : Poor fit / Disadvantage
- -- : Very poor fit / Strong disadvantage

## Decision
We zullen MongoDB implementeren als onze primaire database-oplossing voor zowel de reis- als gebruikersdatabases. MongoDB's document-gebaseerde architectuur sluit perfect aan bij onze behoeften, waardoor we gegevens kunnen opslaan in flexibele, JSON-achtige documenten. Deze structuur is bijzonder goed geschikt voor ons reisplanning- en boekingssysteem, waar datastructuren kunnen variëren en evolueren in de loop van de tijd.

De beslissing om MongoDB te gebruiken is gebaseerd op verschillende belangrijke factoren. MongoDB's schema-loze ontwerp biedt de flexibiliteit die we nodig hebben om onze datamodellen aan te passen naarmate de applicatie evolueert. De document-gebaseerde structuur vertegenwoordigt natuurlijk onze reis- en gebruikersgegevens, waardoor het gemakkelijker wordt om complexe relaties te modelleren. MongoDB's horizontale schaalbaarheidsmogelijkheden via sharding zullen ons in staat stellen onze applicatie te schalen naarmate onze gebruikersbasis groeit. De rijke querytaal en aggregatieframework zullen ons in staat stellen complexe queries en data-analyse efficiënt uit te voeren.

## Consequences
Het gebruik van MongoDB zal verschillende significante voordelen bieden aan onze applicatie. Het flexibele schema-ontwerp zal het gemakkelijker maken om te itereren op onze datamodellen zonder complexe migraties te vereisen. De document-gebaseerde structuur zal de representatie van geneste gegevens vereenvoudigen, wat gebruikelijk is in reis- en boekingsinformatie. MongoDB's ingebouwde ondersteuning voor georuimtelijke queries zal waardevol zijn voor locatie-gebaseerde functies in ons reisplanning systeem. De mogelijkheid om horizontaal te schalen zal ervoor zorgen dat onze applicatie groeiende datavolumes en gebruikersbelasting aankan.

Het implementeren van MongoDB introduceert echter ook bepaalde uitdagingen. Het ontbreken van strikte schema-validatie vereist zorgvuldige validatie op applicatieniveau om gegevensintegriteit te waarborgen. Het uiteindelijke consistentiemodel kan extra overweging vereisen voor bepaalde use cases waar sterke consistentie cruciaal is. We zullen onze indexen zorgvuldig moeten ontwerpen om queryprestaties te optimaliseren, en het geheugengebruik kan hoger zijn in vergelijking met traditionele relationele databases.

## Alternatives Considered
We hebben verschillende alternatieven zorgvuldig geëvalueerd voordat we voor MongoDB kozen. PostgreSQL werd overwogen vanwege zijn sterke ACID-compliance en robuuste relationele functies, maar zijn rigide schemastructuur zou het moeilijker maken om aan te passen aan onze evoluerende gegevensvereisten. MySQL was een andere optie die goede prestaties en betrouwbaarheid biedt, maar mist de flexibiliteit en schaalbaarheidsfuncties die we nodig hebben voor onze groeiende applicatie. CouchDB werd geëvalueerd vanwege zijn uitstekende offline ondersteuning en uiteindelijke consistentiemodel, maar zijn beperkte querymogelijkheden en kleinere community maakten het minder geschikt voor onze behoeften, ondanks zijn sterke document-gebaseerde architectuur.

## Conclusion
MongoDB is de optimale keuze voor onze database-behoeften, die de juiste balans biedt tussen flexibiliteit, schaalbaarheid en prestaties voor zowel onze reis- als gebruikersdatabases. Hoewel het zorgvuldige overweging van gegevensconsistentie en schemadesign vereist, wegen de voordelen van flexibele datamodellering, horizontale schaling en rijke querymogelijkheden op tegen de uitdagingen. Deze keuze zal ons in staat stellen een robuuste en schaalbare applicatie te bouwen die zich kan aanpassen aan veranderende vereisten en groeiende gebruikersbehoeften.

## 9. Deployment, Operation and Support



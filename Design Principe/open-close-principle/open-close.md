# Open/Closed Principle (OCP)

Het Open/Closed Principle, bedacht door Bertrand Meyer in 1988, is een fundamenteel principe in object-georiënteerd ontwerp. De kern van dit principe is dat software entiteiten - zoals classes, modules en functies - open moeten zijn voor uitbreiding, maar gesloten voor modificatie. Dit betekent dat je nieuwe functionaliteit kunt toevoegen zonder bestaande code aan te passen.

## Waarom is dit principe belangrijk?

Het Open/Closed Principle biedt verschillende belangrijke voordelen voor softwareontwikkeling. Ten eerste vermindert het het risico op bugs bij aanpassingen, omdat bestaande code niet wordt gewijzigd. Dit leidt tot betere onderhoudbaarheid en maakt het systeem makkelijker uitbreidbaar. Bovendien bevordert het de herbruikbaarheid van code door het gebruik van abstractie en interfaces.

Echter, het implementeren van dit principe brengt ook enkele uitdagingen met zich mee. Het vereist meer vooruitdenken in het design en kan leiden tot meer abstractie en complexiteit. De initiële ontwikkeltijd kan hierdoor langer zijn, maar deze investering betaalt zich vaak terug in de lange termijn.

## Design Properties

Het OCP is gebaseerd op vier belangrijke design properties:

-   **Maintainability**: Door het principe te volgen, is de code makkelijker te onderhouden omdat bestaande code niet gewijzigd hoeft te worden.
-   **Flexibility**: Nieuwe functionaliteit kan worden toegevoegd zonder bestaande code aan te passen.
-   **Reusability**: Door abstractie en interfaces kunnen componenten hergebruikt worden.
-   **Extensibility**: Het systeem is eenvoudig uit te breiden met nieuwe functionaliteit.

## Code Voorbeeld

Laten we het Open/Closed Principle illustreren met een praktisch voorbeeld van een betalingssysteem. In het bestand `main.java` zien we twee verschillende implementaties:

### Slechte Implementatie (schendt OCP)

```java
class Betaling {
    public void verwerkBetaling(String betaalType, double bedrag) {
        if (betaalType.equals("ideal")) {
            System.out.println("Verwerk iDEAL betaling van €" + bedrag);
        }
        else if (betaalType.equals("creditcard")) {
            System.out.println("Verwerk creditcard betaling van €" + bedrag);
        }
        // Bij nieuwe betaalmethode moet de code aangepast worden!
    }
}
```

In deze implementatie moet de code aangepast worden voor elke nieuwe betaalmethode, wat het Open/Closed Principle schendt. Bijvoorbeeld, als we PayPal willen toevoegen, moeten we de bestaande code wijzigen:

```java
class Betaling {
    public void verwerkBetaling(String betaalType, double bedrag) {
        if (betaalType.equals("ideal")) {
            System.out.println("Verwerk iDEAL betaling van €" + bedrag);
        }
        else if (betaalType.equals("creditcard")) {
            System.out.println("Verwerk creditcard betaling van €" + bedrag);
        }
        else if (betaalType.equals("paypal")) {  // Nieuwe code toevoegen
            System.out.println("Verwerk PayPal betaling van €" + bedrag);
        }
    }
}
```

Dit is problematisch omdat:

1. We bestaande code moeten wijzigen
2. Het risico op fouten toeneemt
3. We moeten de hele methode opnieuw testen
4. Het schendt het "gesloten voor modificatie" principe

### Goede Implementatie (volgt OCP)

```java
interface BetaalMethode {
    void verwerk(double bedrag);
}

class IDEALBetaling implements BetaalMethode {
    @Override
    public void verwerk(double bedrag) {
        System.out.println("Verwerk iDEAL betaling van €" + bedrag);
    }
}

class CreditCardBetaling implements BetaalMethode {
    @Override
    public void verwerk(double bedrag) {
        System.out.println("Verwerk creditcard betaling van €" + bedrag);
    }
}

class BetalingsVerwerker {
    public void verwerkBetaling(BetaalMethode betaalMethode, double bedrag) {
        betaalMethode.verwerk(bedrag);
    }
}
```

Deze implementatie volgt het Open/Closed Principle door:

1. Een `BetaalMethode` interface te definiëren
2. Concrete implementaties te maken voor verschillende betaalmethodes
3. Een `BetalingsVerwerker` te gebruiken die werkt met de interface

### Nieuwe Functionaliteit Toevoegen

We kunnen eenvoudig nieuwe betaalmethodes toevoegen zonder bestaande code aan te passen:

```java
class PayPalBetaling implements BetaalMethode {
    @Override
    public void verwerk(double bedrag) {
        System.out.println("Verwerk PayPal betaling van €" + bedrag);
    }
}
```

## Praktische toepassing

In de praktijk betekent dit dat je bijvoorbeeld bij een betalingssysteem nieuwe betaalmethodes kunt toevoegen zonder de bestaande code aan te passen. In een slecht ontworpen systeem zou je voor elke nieuwe betaalmethode de bestaande code moeten wijzigen. Met het Open/Closed Principle gebruik je interfaces en abstractie om het systeem flexibel te maken. De BetalingsVerwerker class hoeft nooit aangepast te worden, ongeacht hoeveel nieuwe betaalmethodes er worden toegevoegd.

## Conclusie

Het Open/Closed Principle is essentieel voor het creëren van robuuste en flexibele software architectuur. Het draagt bij aan de lange-termijn onderhoudbaarheid van software en maakt systemen toekomstbestendig. Door het principe correct toe te passen, kun je software ontwikkelen die niet alleen vandaag werkt, maar ook morgen eenvoudig kan worden uitgebreid met nieuwe functionaliteit.

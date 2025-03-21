
## Data MappiThe key mapping between domain model and API is:

| Domain Model Attribute | API Parameter/Response |
|------------------------|------------------------|
| Verblijf.startDatum    | checkin_date           |
| Verblijf.eindDatum     | checkout_date          |
| Verblijfplaats.locatie | dest_id, dest_type     |
| Verblijfplaats.prijs   | price_breakdown.gross_price |
| Locatie.lat            | latitude               |
| Locatie.lon            | longitude              |

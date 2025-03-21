# Booking.com API Client

## Setup

To use the Booking.com API client, you need to:

1. Create a `.env` file in the root of your project
2. Add your Booking.com API key as follows:
   ```
   BOOKING_API_KEY=your_api_key_here
   ```
3. Make sure to keep your `.env` file private (it's already added to `.gitignore`)

## Running the Test Script

To run the test script:

```bash
# Run with environment variable from .env file
node -r dotenv/config test-booking-api.js

# Or provide API key as a command-line argument
node test-booking-api.js your_api_key_here
```

Note: You need to install the `dotenv` package if you don't have it already:
```bash
npm install dotenv
```

## Data Mapping

The key mapping between domain model and API is:

| Domain Model Attribute | API Parameter/Response |
|------------------------|------------------------|
| Verblijf.startDatum    | checkin_date           |
| Verblijf.eindDatum     | checkout_date          |
| Verblijfplaats.locatie | dest_id, dest_type     |
| Verblijfplaats.prijs   | price_breakdown.gross_price |
| Locatie.lat            | latitude               |
| Locatie.lon            | longitude              |

// Import the BookingApiClient class from the main file
require('./booking-api-client');

// API key from environment variable, with fallback to command line argument
const API_KEY = process.env.BOOKING_API_KEY || process.argv[2];

// Helper function to get dates for testing
function getFutureDates() {
  const today = new Date();
  
  // Check-in date: 30 days from now
  const checkinDate = new Date(today);
  checkinDate.setDate(today.getDate() + 30);
  
  // Check-out date: 35 days from now (5-day stay)
  const checkoutDate = new Date(today);
  checkoutDate.setDate(today.getDate() + 35);
  
  // Format dates as YYYY-MM-DD
  const formatDate = (date) => {
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}-${month}-${day}`;
  };
  
  return {
    checkin: formatDate(checkinDate),
    checkout: formatDate(checkoutDate)
  };
}

// Test function that runs all API calls in sequence
async function testBookingApi() {
  try {
    console.log('Creating BookingApiClient...');
    const client = new BookingApiClient(API_KEY);
    
    // Step 1: Search for locations
    console.log('\n--- SEARCHING LOCATIONS ---');
    const locations = await client.searchLocations('Amsterdam', 'nl');
    
    // Log the full response to see what we're getting
    console.log('Location search response:');
    console.log(JSON.stringify(locations, null, 2));
    
    // Check if we have valid location data
    if (!locations || !Array.isArray(locations) || locations.length === 0) {
      console.log('No valid location data returned. Using default location for Amsterdam.');
      // Use default values for Amsterdam
      const defaultLocationId = '1060';
      const defaultLocationType = 'city';
      
      // Continue with hotel search using default values
      await searchHotels(client, defaultLocationId, defaultLocationType);
    } else {
      // Extract location ID and type for hotel search
      const locationId = locations[0].dest_id;
      const locationType = locations[0].dest_type;
      console.log(`Using location: ${locations[0].name}, ID: ${locationId}, Type: ${locationType}`);
      
      // Continue with hotel search
      await searchHotels(client, locationId, locationType);
    }
    
    console.log('\nTests completed successfully!');
  } catch (error) {
    console.error('Error during API testing:', error);
  }
}

// Separate function for hotel search
async function searchHotels(client, locationId, locationType) {
  try {
    // Step 2: Search for hotels
    console.log('\n--- SEARCHING HOTELS ---');
    
    // Get future dates for testing
    const dates = getFutureDates();
    console.log(`Using dates: Check-in ${dates.checkin}, Check-out ${dates.checkout}`);
    
    // Create test data from domain model
    const trip = {
      aantalVolwassenen: 2,
      aantalKamers: 1,
      valuta: 'EUR',
      taal: 'nl'
    };
    
    const verblijf = {
      startDatum: dates.checkin,
      eindDatum: dates.checkout,
      verblijfplaats: {
        locationId: locationId,
        locationType: locationType
      }
    };
    
    console.log(`Searching hotels for location ID: ${locationId}, type: ${locationType}`);
    const hotels = await client.searchHotels(verblijf, trip);
    
    if (!hotels || !Array.isArray(hotels) || hotels.length === 0) {
      console.log('No hotels found or invalid response.');
      return;
    }
    
    console.log(`Found ${hotels.length} hotels:`);
    console.log(JSON.stringify(hotels[0], null, 2));
    
    // Step 3: Get hotel details
    if (hotels.length > 0 && hotels[0].hotelId) {
      await getHotelDetails(client, hotels[0].hotelId);
    }
  } catch (error) {
    console.error('Error searching hotels:', error);
  }
}

// Separate function for hotel details
async function getHotelDetails(client, hotelId) {
  try {
    console.log('\n--- GETTING HOTEL DETAILS ---');
    console.log(`Getting details for hotel ID: ${hotelId}`);
    
    const hotelDetails = await client.getHotelDetails(hotelId, 'nl');
    
    if (!hotelDetails) {
      console.log('No hotel details found or invalid response.');
      return;
    }
    
    console.log('Hotel details:');
    console.log(JSON.stringify(hotelDetails, null, 2));
  } catch (error) {
    console.error('Error getting hotel details:', error);
  }
}

// Run the tests
testBookingApi(); 
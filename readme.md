## API Endpoints
### 1. Transaction Data
**Endpoint**: `GET /api/transactions/{address}`  
**Response**:
- Total swaps count
- Total transaction volume in ETH
- Total transaction volume in USD
- List of transactions

### 2. Balance
**Endpoint**: `GET /api/balance/{address}`  
**Response**:
- Current Ethereum balance of the address

### 3. Assets
**Endpoint**: `GET /api/assets/{address}`  
**Response**:
- List of all tokens held by the address along with their USD value


## Technologies Used
- **Java 23**
- **Spring Boot**
- **Etherscan API**
- **CoinGecko API**
- **Jackson (JSON Processing)**
- **RestTemplate (HTTP Client)**

## Running the Application
1. Clone the repository:
   ```bash
   git clone https://github.com/example/hauck-evm-monitor.git
   
2. Navigate to the project directory:
    ```bash
   cd hauck-evm-monitor
3. Set up your application.properties file under src/main/resources/ with your Etherscan API key:
    ```bash
    etherscan.api.key="YOU_API_KEY"
    etherscan.assets.api = https://api.etherscan.io/api?module=account&action=tokentx&address=%s&apikey=%s
    etherscan.tx.api = https://api.etherscan.io/api?module=account&action=txlist&address=%s&startblock=0&endblock=99999999&sort=desc&apikey=%s"

4. Clean
    ```bash
   mvn clean install
   
5. Running the app
    ```bash
    mvn spring-boot:run 
   
6. Access the API via `http://localhost:8080/api/{endpoint}`
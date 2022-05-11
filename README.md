# CS122B Backend 4 - The Gateway Service

#### [Application](#application)

- [pom.xml](#pomxml)
- [application.yml](#applicationyml)
- [Resources](#resources)
- [Tests](#tests)

#### [Database](#database)

- [Schemas](#schemas)
- [Tables](#tables)

#### [Routes](#routes)

- [IDM](#idm)
- [Movies](#movies)
- [Billing](#billing)

#### [Filters](#filters)

- [GlobalLoggingFilter](#globalloggingfilter)
- [AuthFilter](#authfilter)

## Application

Our application depends on a lot of files and resources to be able to run correctly. These files
have been provided for you and are listed here for your reference. These files should **NEVER** be
modified and must be left **AS IS**.

### pom.xml

Maven gets all its settings from a file called `pom.xml`. This file determines the dependencies we
will use in our project as well as the plugins we use for compiling, testing, building, ect..

- [pom.xml](pom.xml)

### application.yml

Spring Boot has a large number of settings that can be set with a file called `application.yml`. We
have already created this file for you and have filled it with some settings. There is a file for
the main application as well as one for the tests.

- [Main application.yml](/src/main/resources/application.yml)
- [Test application.yml](/src/test/resources/application.yml)

### Resources

There are two folders in this project that contain resources, and application settings, as well as
files required for the tests.

- [Main Resources](/src/main/resources)
- [Test Resources](/src/test/resources)

### Tests

There is a Single class that contain all of our test cases:

- [GatewayServiceTest](/src/test/java/com/github/klefstad_teaching/cs122b/gateway/GatewayServiceTest.java)

## Database

### Schemas

<table>
  <thead>
    <tr>
      <th align="left" width="1100">🗄 gateway</th>
    </tr>
  </thead>
</table>

### Tables

<table>
  <tbody>
    <tr>
      <th colspan="3" align="left" width="1100">💾 gateway.request</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left" width="175">Column Name</th>
      <th align="left" width="175">Type</th>
      <th align="left">Attributes</th>
    </tr>
    <tr>
      <td>id</td>
      <td><code>INT</code></td>
      <td><code>NOT NULL</code> <code>PRIMARY KEY</code> <code>AUTO_INCREMENT</code></td>
    </tr>
    <tr></tr>
    <tr>
      <td>ip_address</td>
      <td><code>VARCHAR(64)</code></td>
      <td><code>NOT NULL</code></td>
    </tr>
    <tr></tr>
    <tr>
      <td>call_time</td>
      <td><code>TIMESTAMP</code></td>
      <td><code>NOT NULL</code></td>
    </tr>
    <tr></tr>
    <tr>
      <td>path</td>
      <td><code>VARCHAR(2048)</code></td>
      <td><code>NULL</code></td>
    </tr>
  </tbody>
</table>

## Routes

### IDM

<table>
  <tbody >
    <tr>
      <th colspan="2" align="left" width="1100">🧳&nbsp;&nbsp;Route</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left">Path Redirect </th>
      <th align="left">Example</th>
    </tr>
    <tr>
      <td align="left"><code>/idm/(restOfPath) -> /(restOfPath)</code></td>
      <td align="left"><code>/idm/login -> /login</code></td>
    <tr></tr>
    <tr>
      <td colspan="2" align="left">Takes all requests that start with <code>/idm</code> and redirect the call to the idm without the <code>/idm</code> prefix</td>
    </tr>
  </tbody>
</table>

### Movies

<table>
  <tbody >
    <tr>
      <th colspan="2" align="left" width="1100">🧳&nbsp;&nbsp;Route</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left">Path Redirect </th>
      <th align="left">Example</th>
    </tr>
    <tr>
      <td align="left"><code>/movies/(restOfPath) -> /(restOfPath)</code></td>
      <td align="left"><code>/movies/movie/search -> /movie/search</code></td>
    <tr></tr>
    <tr>
      <td colspan="2" align="left">Takes all requests that start with <code>/movies</code> and redirect the call to the idm without the <code>/movies</code> prefix</td>
    </tr>
    <tr>
      <th colspan="2" align="left" width="1100">🎛️&nbsp;&nbsp;Filter</th>
    </tr>
    <tr>
        <td colspan="2" ><code>AuthFilter</code></td>
    </tr>
  </tbody>
</table>

### Billing

<table>🎛️
  <tbody >
    <tr>
      <th colspan="2" align="left" width="1100">🧳&nbsp;&nbsp;Route</th>
    </tr>
    <tr></tr>
    <tr>
      <th align="left">Path Redirect </th>
      <th align="left">Example</th>
    </tr>
    <tr>
      <td align="left"><code>/billing/(restOfPath) -> /(restOfPath)</code></td>
      <td align="left"><code>/billing/login -> /login</code></td>
    <tr></tr>
    <tr>
      <td colspan="2" align="left">Takes all requests that start with <code>/billing</code> and redirect the call to the idm without the <code>/billing</code> prefix</td>
    </tr>
    <tr><td colspan="2" ></td></tr>
    <tr></tr> 
    <tr>
      <th colspan="2" align="left" width="1100">🎛️&nbsp;&nbsp;Filter</th>
    </tr>
    <tr>
        <td colspan="2" ><code>AuthFilter</code></td>
    </tr>
  </tbody>
</table>

## Filters

### GlobalLoggingFilter

<table>
  <tbody>
    <tr>
      <th  align="left" width="1100">🎛️&nbsp;&nbsp;Filter</th>
    </tr>
  </tbody>
</table>

### AuthFilter

<table>
  <tbody>
    <tr>
      <th  align="left" width="1100">🎛️&nbsp;&nbsp;Filter</th>
    </tr>
  </tbody>
</table>


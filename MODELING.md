## USE CASE 1 : Navigasjon til activityscreen  ved å trykke på kartet:
![image](https://media.github.uio.no/user/9127/files/f6fc1281-a721-4d24-856e-8eceef610e3b)

![image](https://media.github.uio.no/user/9127/files/e760c536-13c5-40bf-97ae-04ccab8c1950)


Dette er en av måtene bruker kan bruke kartet for å komme seg til aktivitetsskjermen for å bruke dataen som appen henter.
Når bruker trykker på kartet zoomes det inn til der de trykket og det kommer et kort som enten viser stedsnavnet og lar bruker navigere dit, eller så får bruker beskjed
om at det ikke er noen havdata der, da kan de bare lukke kortet.


## USE CASE 2 : Navigasjon til activityscreen ved hjelp av søkebar:
![image](https://media.github.uio.no/user/9127/files/ca5d0d0f-89d9-4615-b320-ad92fa6e2e09)

![image](https://media.github.uio.no/user/9127/files/f2c84fe6-9f9f-4886-a885-54d96a31e5e5)


Dette er en måte å navigere til activityscreen som tilbys ved siden av å trykke på kartet.
Brukeren kan skrive inn tekst i søkeboksen, denne teksten sendes gjennom lagene til geocodedatasource og brukes til et API kall og returnerer 5 stedsnavn.
Disse stedsnavnene blir puttet i SearchUIState og presentert for bruker som en dropdown fra søkeboksen. Om bruker velger en av disse vil man oppleve samme handlingsforløp
som use case 1. Altså vil kartet zoome inn og vise et kort som enten lar deg navigere til activity screen eller sier at det ikke er noen havdata.

## USE CASE 3: ActivityScreen etter navigasjon 
![image](https://media.github.uio.no/user/9127/files/f7561e15-cfd9-4442-a5b0-a82fa814098a)
![image](https://media.github.uio.no/user/9127/files/07be87b5-3354-4959-8e5f-3408696caf68)


ActivityScreen har hovedsakelig en funksjon, dette er å bytte mellom aktiviteter som oppdaterer UIStaten til anbefalingsbaren.
Når brukeren navigerer til activityscreen lastes inn alle API'ene og blir oppdatert i UIstates til activityviewmodel.
Deretter brukes dataen fra API'ene til anbefalingsalgoritmen for bading, denne er satt som standard.
Bruker kan deretter bruke knappen nede til venstre for å bytte mellom de fire aktivitetene som vil oppdatere anbefalingsbaren.








   
  

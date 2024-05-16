![image](https://media.github.uio.no/user/9127/files/f87d4a47-843e-4b26-86af-1b587f85f062)


# Architecture Overview

## Innledning

Denne dokumentasjonen gir en detaljert oversikt over arkitekturen til "Applikasjonsnavn". Målet er å hjelpe de som er ansvarlige for drift, vedlikehold, og videreutvikling av løsningen å få en grundig forståelse av designvalgene, teknologiene som er brukt og kommunikasjonsflyten i appen.

### Model-View-ViewModel (MVVM)

Vår applikasjon bruker MVVM-arkitektur for å fremme separasjon av bekymringer, gjøre koden mer testbar og forenkle vedlikehold. MVVM-arkitekturen deler applikasjonen inn i tre hovedkomponenter:

- **Model**: Representerer domenedata og forretningslogikk.
- **View**: UI-komponenter (brukergrensesnittet) som viser data til brukeren.
- **ViewModel**: En abstraksjon av viewet som håndterer presentasjonslogikk og statustilstander.

### Unidirectional Data Flow (UDF)

Vi følger UDF-prinsippet for å håndtere staten i en forutsigbar måte. Dette sikrer at data har en enkeltflyt gjennom systemet og gjør det lett å spore endringer over tid.

## Lav kobling og høy kohesjon

Alle funksjonaliteter er delt opp i mindre funskjoner og objekter for å begrense deres arbeidsområder

## Teknologier og rammer

- **Android Studio**: Utviklingsmiljø.
- **Kotlin**: Programmeringsspråk.
- **Jetpack Compose**: Moderne UI-verktøysett for native Android UI-utvikling.

## API-nivå og Android-versjon


Denne applikasjonen retter seg mot Android API-nivå 34 med et minimum API-nivå 24. Dette valget ble gjort for å sikre bred kompatibilitet med enheter samtidig som man har tilgang til de nyeste funksjonene og forbedringene til Android-rammeverket.

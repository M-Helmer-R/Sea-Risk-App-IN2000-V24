![image](https://media.github.uio.no/user/9127/files/e27854a6-2cc3-4ed1-980b-1e1666c96f8f)


Appen benytter seg av en MVVM arkitektur. API kallene skjer i datakildene og blir sendt gjennom repositoriene til viewmodelsene og oppdaterer UIStates i viewen.
Dataflyten går begge veier, dette vil si at API kall skjer basert på interksjon med brukergrensesnittet, og brukergrensesnittet endrer seg basert på resultatene av API kallene.
De ulike lagene er delt opp ved hjelp av objektorienterte prinsipper, og hvert lag kan byttes ut. En kan for eksempel bytte ut repositorien en viewmodel bruker uten problemer så lenge den implementerer de samme funksjonene.
Strukturen opprettholder også høy kohesjon ved at alle de ulike delene objektene utfører egne små oppgaver. Et eksempel på dette er data kildene som kun henter JSON data og konverterer det til data klasser..
Vi valgte API nivå 24 fordi denne fungerte bra med funksjonene vi trengte samtidig som den funker på de aller fleste telefoner.


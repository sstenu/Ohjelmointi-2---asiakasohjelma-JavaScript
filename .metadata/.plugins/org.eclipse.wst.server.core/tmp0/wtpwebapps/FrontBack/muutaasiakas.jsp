<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<script src="scripts/main.js"></script>
<link rel="stylesheet" type="text/css" href="css/tyyli.css">
<title>Asiakkaan muutos</title>
</head>
<body onkeydown="tutkiKey(event)">
	<form id="tiedot">
		<table>
			<thead>
				<tr>
					<th colspan="4" id="ilmo"></th>
					<th colspan="2" class="oikealle"><a href="listaaasiakkaat.jsp" id="takaisin">Takaisin listaukseen</a></th>
				</tr>
				<tr>
					<th>Asiakasnumero</th>
					<th>Etunimi</th> 
					<th>Sukunimi</th>
					<th>Puhelin</th>
					<th>Sposti</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td><input type="text" name="asiakasID" id="asiakasID"></td>
					<td><input type="text" name="etunimi" id="etunimi"></td>
					<td><input type="text" name="sukunimi" id="sukunimi"></td>
					<td><input type="text" name="puhelin" id="puhelin"></td> 
					<th><input type="text" name="sposti" id="sposti"></th>
					<td><input type="button" id="tallenna" value="Hyv�ksy" onclick="vieTiedot()"></td>
				</tr>
			</tbody>		
		</table>
		<input type="hidden" name="vanhaID" id="vanhaID">
	</form>
</body>

<script>

function tutkiKey(event){
	if(event.keyCode==13){//Enter
		vieTiedot();
	}		
}

document.getElementById("asiakasID").focus();//vied��n kursori asiakasID-kentt��n sivun latauksen yhteydess�

//Haetaan muutettavan asiakkaan tiedot. Kutsutaan backin GET-metodia ja v�litet��n kutsun mukana muutettavan tiedon id
//GET /asiakkaat/haeyksi/id
var asiakasID = requestURLParam("asiakasID"); //Funktio l�ytyy scripts/main.js 
fetch("asiakkaat/haeyksi/" + asiakasID,{//L�hetet��n kutsu backendiin
      method: 'GET'	      
    })
.then( function (response) {//Odotetaan vastausta ja muutetaan JSON-vastausteksti objektiksi
	return response.json()
})
.then( function (responseJson) {//Otetaan vastaan objekti responseJson-parametriss�	
	console.log(responseJson);
	document.getElementById("asiakasID").value = responseJson.asiakasID;		
	document.getElementById("etunimi").value = responseJson.etunimi;	
	document.getElementById("sukunimi").value = responseJson.sukunimi;	
	document.getElementById("puhelin").value = responseJson.puhelin;	
	document.getElementById("sposti").value = responseJson.sposti;
	document.getElementById("vanhaID").value = responseJson.asiakasID;	
});	

//Funktio tietojen muuttamista varten. Kutsutaan backin PUT-metodia ja v�litet��n kutsun mukana muutetut tiedot json-stringin�.
//PUT /autot/
function vieTiedot(){	
	var ilmo="";
	if(document.getElementById("asiakasID").value.length<1){
		ilmo="Asiakasnumero ei kelpaa!";		
	}else if(document.getElementById("etunimi").value.length<2){
		ilmo="Etunimi ei kelpaa!";		
	}else if(document.getElementById("sukunimi").value.length<1){
		ilmo="Sukunimi ei kelpaa!";		
	}else if(document.getElementById("puhelin").value*1!=document.getElementById("puhelin").value){
		ilmo="Puhelin ei ole luku!";		
	}else if(document.getElementById("sposti").value.length<5){
		ilmo="S�hk�posti ei kelpaa!";		
	}
	if(ilmo!=""){
		document.getElementById("ilmo").innerHTML=ilmo;
		setTimeout(function(){ document.getElementById("ilmo").innerHTML=""; }, 3000);
		return;
	}
	document.getElementById("asiakasID").value=siivoa(document.getElementById("asiakasID").value);
	document.getElementById("etunimi").value=siivoa(document.getElementById("etunimi").value);
	document.getElementById("sukunimi").value=siivoa(document.getElementById("sukunimi").value);
	document.getElementById("puhelin").value=siivoa(document.getElementById("puhelin").value);	
	document.getElementById("sposti").value=siivoa(document.getElementById("sposti").value);
	
	var formJsonStr=formDataToJSON(document.getElementById("tiedot")); //muutetaan lomakkeen tiedot json-stringiksi
	console.log(formJsonStr);
	//L�het��n muutetut tiedot backendiin
	fetch("asiakkaat",{//L�hetet��n kutsu backendiin
	      method: 'PUT',
	      body:formJsonStr
	    })
	.then( function (response) {//Odotetaan vastausta ja muutetaan JSON-vastaus objektiksi
		return response.json();
	})
	.then( function (responseJson) {//Otetaan vastaan objekti responseJson-parametriss�	
		var vastaus = responseJson.response;		
		if(vastaus==0){
			document.getElementById("ilmo").innerHTML= "Tietojen p�ivitys ep�onnistui";
        }else if(vastaus==1){	        	
        	document.getElementById("ilmo").innerHTML= "Tietojen p�ivitys onnistui";			      	
		}	
		setTimeout(function(){ document.getElementById("ilmo").innerHTML=""; }, 5000);
	});	
	document.getElementById("tiedot").reset(); //tyhjennet��n tiedot -lomake
}
</script>
</html>
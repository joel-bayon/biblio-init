function control(form) {
	var input = form.nom.value;
	if ((input == null) || (input == "")) 
	{ 
		alert("champ login obligatoire !");
		return false;
	}
	input = form.motDePasse.value;
	if ((input == null) || (input == "")) 
	{ 
		alert("champ mot de passe obligatoire !");
		return false;
	}
	return true; 
}

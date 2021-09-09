/**
 * Confirmador de exclusão de contato
 */
function confirmar(idcon){
	let resposta = confirm ("confirma a exclusão deste contato?")
	if(resposta === true){
		//alert(idcon)
		//vair transfeir i Id para o Controller como parametro o idcon
		window.location.href = "delete?idcon=" + idcon
	}
}
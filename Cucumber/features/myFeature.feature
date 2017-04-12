Feature: 
	Mostrar Ejemplo de Feature con Youtube
	So that
	
	Background: Visitar Youtube
		Given Yo he visitado Youtube
	
	Scenario: Buscar vídeos de "Metodología ágil"
		When Busco "Metodología ágil"
		Then Recibo los vídeos sobre "Metodología ágil" en Youtube
	
	Scenario: Suscribirme a un canal 
		When Me gusta el contenido de un canal de Youtube
		Then Me suscribo a su canal
		And Me aparecen sus vídeos en la página principal
	
	Scenario: Dar me gusta a un vídeo
		When Veo un vídeo de Youtube
		And Me gusta el vídeo
		Then Pulso el botón de "Me gusta"
		
	Scenario: Registro
		Given No tengo cuenta en Youtube
		When Quiero tener una cuenta en Youtube
		Then Introduzco mis datos en la plataforma
		And Tengo una nueva cuenta en Youtube
		
	Scenario: Subir un vídeo
		Given Tengo una cuenta en Youtube
		When Quiero subir un vídeo a la plataforma
		Then Pulso "Subir"
		And Adjunto el vídeo
		And Subo el vídeo a la plataforma
		
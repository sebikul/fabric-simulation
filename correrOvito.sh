#!/bin/bash 
## script para graficar con ovito y el colormap pedido. Aca se setean parametros y luego se invoca a ovito con la configuración de colormap que esta en "ovitoScript.py".
## IMPORTANTE: Se debe setear ubicacion del binario de ovito y el archivo de datos de simulacion
## IMPORTANTE:para que funcione, se tiene que estar en la base del directorio del trabajo(en donde esta el archivo ovitoScript.py).


# el binario ovito debe apuntar al ejecutable "ovitos" no a "ovito".
binario_Ovito='/home/administrator/Downloads/ovito-2.6.2-x86_64/bin/ovitos'

simulationData="/home/administrator/fabric-simulation/simulations/FABRIC_SIM__W=10_H=10_STEP=1.00000e-05_WSTEP=0.100000_GRAV=true_DAMPING=true.xyz"

ovitoScript="./ovitoScript.py"

# Se abre la interfaz gráfica de ovito con el colormap seteado
# El parámetro "-g" es el que activa la interfaz grafica.Si lo sacas, no se ve graficamente y se trabaja solo por consola.

commandLine="$binario_Ovito -g $ovitoScript $simulationData"
#echo "$commandLine"
eval "$commandLine"

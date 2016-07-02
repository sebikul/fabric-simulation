import ovito
from ovito.io import *
from ovito.modifiers import *
from ovito.vis import *
import numpy


# se carga el archivo de datos de la simulación
node = import_file("/home/administrator/fabric-simulation/simulations/FABRIC_SIM__W=10_H=5_STEP=0.000100000_WSTEP=0.100000.xyz", columns=["Particle Identifier", "Position.X", "Position.Y", "Position.Z", "Velocity.X", "Velocity.Y", "Velocity.Z", "Radius","Velocity Magnitude"], multiple_frames = True)

print("Total frames:",(ovito.dataset.anim.last_frame+1))

# se oculta la celda de ovito
cell = node.source.cell
cell.display.enabled = False  

#se apaga la visualizacion temporalmente
disp = node.source.particle_properties.position.display
disp.enabled=False


# Se itera por todos los cuadros para obtener máximos y minimos de velocidad(en modulo):
velocity_maximum=0
velocity_minimum=0
for f in range(ovito.dataset.anim.last_frame+1):
	#print("Frame:",f)
	# Set the time slider position:
	ovito.dataset.anim.current_frame = f
	node.compute()
	#norma 2 de la velocidad
	velocityMagnitudes = node.output.particle_properties.velocity_magnitude.array
	#print(velocityMagnitudes) 
	current_maximum=max(velocityMagnitudes)
	current_minimum=min(velocityMagnitudes)
	
	velocity_maximum=max(current_maximum,velocity_maximum)
	velocity_minimum=min(current_minimum,velocity_minimum)

print("Velocity max:",velocity_maximum)
print("Velocity min:",velocity_minimum)


# se setean los modificadores de colores segun magnitud de velocidad

modifier = ColorCodingModifier(
    property = "Velocity Magnitude",
    gradient = ColorCodingModifier.Hot(),
    start_value = float(velocity_minimum),
    end_value = float(velocity_maximum)
)
node.modifiers.append(modifier)

#se vuelve a encender la visualizacion
disp.enabled=True

# se resetea la vista al primer cuadro
ovito.dataset.anim.current_frame = 0
node.compute()



#fin
print("finished")

"""

mod = AssignColorModifier( color=(0.5, 1.0, 0.0) )
node.modifiers.append(mod)


vp = Viewport()
vp.type = Viewport.Type.PERSPECTIVE
vp.camera_pos = (-100, -150, 150)
vp.camera_dir = (2, 3, -3)
vp.fov = math.radians(60.0)

settings = RenderSettings()
"""

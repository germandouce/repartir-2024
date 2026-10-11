# language: es

Característica: Registrar gastos grupales

  Regla: El total de un grupo no debe ser negativo
    
    Escenario: Se puede registrar un gasto que no deja el total en negativo
      Dado que un grupo tiene un total de 100 pesos
      Cuando se registra un gasto de -30 pesos
      Entonces el total del grupo es de 70 pesos
        
    Escenario: Se rechaza un gasto que dejaría el total negativo
      Dado que un grupo tiene un total de 100 pesos
      Cuando se intenta registrar un gasto de -150 pesos
      Entonces el gasto no se registra
      Y el total del grupo sigue siendo de 100 pesos        
    
    Escenario: Se puede registrar un gasto que deja el total en cero
      Dado que un grupo tiene un total de 100 pesos
      Cuando se registra un gasto de -100 pesos
      Entonces el total del grupo es de 0 pesos

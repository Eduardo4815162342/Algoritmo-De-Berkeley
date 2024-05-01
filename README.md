
# Sincronização de Tempo de Berkeley - Implementação em Java

Esta aplicação Java demonstra o algoritmo de Sincronização de Tempo de Berkeley usando uma arquitetura cliente-servidor. O servidor gerencia os ajustes de tempo baseados nas discrepâncias entre múltiplos clientes.

## Como Executar a Aplicação

### Passo 1: Iniciar o Servidor
Execute o servidor antes de iniciar quaisquer clientes. O servidor aguardará conexões de até quatro clientes.

**Comando para iniciar o servidor:**
```bash
java com.example.timesync.ServidorDeTempo
```

### Passo 2: Iniciar o(s) Cliente(s)
Após o servidor estar em execução, você pode iniciar os clientes. Cada cliente precisa inserir um horário no formato `dd/MM/yyyy HH:mm:ss` quando solicitado. Isso simula o horário local do cliente.

**Entradas de exemplo:**
- "01/01/2001 10:11:15"
- "10/11/2450 01:00:00"
- "12/03/1999 13:30:00"
- "04/11/2023 13:30:00"

**Comando para iniciar um cliente:**
```bash
java com.example.timesync.Cliente
```

### Saídas
- **Saída do Servidor:** Exibe o horário recebido em milissegundos, a diferença de tempo e o tempo ajustado.
- **Saída do Cliente:** Mostra tanto o horário inicialmente inserido quanto o horário ajustado pelo servidor no formato `dd/MM/yyyy HH:mm:ss`.

## Estrutura do Código

### Servidor (`ServidorDeTempo`)
- Abre um `ServerSocket` e espera por conexões de clientes.
- Gerencia as diferenças de tempo e calcula os ajustes necessários.
- Envia os ajustes de tempo calculados de volta para os clientes.

### Cliente (`Cliente`)
- Conecta-se ao servidor e envia seu horário local.
- Recebe o horário ajustado do servidor e o exibe.

## Requisitos
- SDK Java
- Acesso à rede entre as máquinas do servidor e dos clientes

## Observações
- Certifique-se de que o servidor esteja em execução antes de iniciar quaisquer clientes.
- O servidor está configurado para lidar com até quatro conexões de clientes simultâneas.

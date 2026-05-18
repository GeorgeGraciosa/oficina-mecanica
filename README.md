# Observações sobre o desenvolvimento

Durante o desenvolvimento do sistema de oficina mecânica, alguns pontos exigiram maior atenção por envolverem conceitos aplicados em conjunto, como orientação a objetos, camada de negócio, validações e fluxo entre os modelos.

## Pontos de atenção

- Separação de responsabilidades entre modelo, camada de negócio e futura camada de persistência.
- Modelagem do relacionamento entre `Servico`, `ServicoItem`, `Peca` e `TipoServico`.
- Implementação do cálculo do valor total do serviço usando `BigDecimal`.
- Organização do fluxo principal da aplicação para criação de pessoa, veículo, serviço e itens.

## O que foi implementado

- Criação de pessoas, veículos, peças e tipos de serviço.
- Criação de serviços vinculados as classes.
- Inclusão de itens de serviço do tipo peça e serviço.
- Cálculo do valor total do serviço.
- Finalização do serviço com armazenamento do valor final.
- Teste do fluxo principal em memória.

## Próximos passos

- Criar armazenamento em memória com listas para simular os cadastros.
- Implementar métodos de busca e listagem.
- Implementar listagem de serviços.
- Implementar resumo de serviços por veículo.
- Evoluir posteriormente para persistência com JDBC.

## Conclusão

O desenvolvimento ajudou a consolidar melhor a aplicação de orientação a objetos em um fluxo mais próximo de um sistema real. Os principais desafios estiveram relacionados à organização das responsabilidades entre as classes e à implementação correta das regras de negócio.

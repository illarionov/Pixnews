# redux

Модуль с базовыми Redux классами

### Store

- Хранит стейт приложения
- Source of truth для приложения / экрана
- Позволяет получить к нему доступ через `store.observeState(): StateFlow`
  - Позволяет регистрировать слушателей состояния `flow.subscribe(listener)`
  - Управляет подписками
- Позволяет обновлять стейт через `store.dispatch(action)` (это единственный способ обновить состояние)
- Хранит цепочку выполняемых middleware

### Action

Data object со строковым полем type.
Не параметризован, строки не выносятся в консты, экшены не ограничиваются sealed иерархией.

### Reducer

Чистая Функция, принимающая **state** и **action**, обновляющая стейт, если требуется.
`(State, Action) -> New State`

### Middleware

Перехватывают экшены из Store, могут их заменять перед редьюсером, выполняет запросы, генерирует новые action.
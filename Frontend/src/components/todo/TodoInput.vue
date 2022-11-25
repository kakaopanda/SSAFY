<template>
  <b-container class="bv-example-row">
    <b-row class="text-center">
      <b-col>
        <b-input-group prepend="할일" class="mt-3">
          <b-form-input v-model.trim="todoTitle" @keypress.enter="createTodo"></b-form-input>
          <b-input-group-append>
            <b-button variant="outline-success" @click="createTodo">등록</b-button>
          </b-input-group-append>
        </b-input-group>
      </b-col>
    </b-row>
  </b-container>
</template>

<script>
export default {
  name: "TodoInput",
  data() {
    return {
      todoTitle: "",
    };
  },
  methods: {
    createTodo() {
      const todoItem = {
        title: this.todoTitle,
        completed: false,
      };
      if (todoItem.title) this.$store.dispatch("createTodo", todoItem);
      this.todoTitle = "";
    },
  },
};
</script>

<style></style>

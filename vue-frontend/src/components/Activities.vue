<script>
export default {
  name: "activities-component",
  data() {
    return {
      activities: [],
      searchQuery: "",
    };
  },
  async mounted() {
    const response = await fetch("http://localhost:8080/api/activities", {
      method: "GET",
      headers: {
        Accept: "application/json",
        "Content-Type": "application/json",
      },
    });
    this.activities = await response.json();
  },
  computed: {
    filteredActivities() {
      return this.activities.filter((activity) => {
        return activity.title
          .toLowerCase()
          .includes(this.searchQuery.toLowerCase());
      });
    },
  },
};
</script>

<template>
  <main>
    <input
      type="text"
      v-model="searchQuery"
      placeholder="Search activities..."
      class="search-input"
    />
    <div class="activities__container">
      <div
        class="activities__activity"
        v-for="activity in filteredActivities"
        :key="activity.id"
      >
        <h3>{{ activity.title }}</h3>
        <p>{{ activity.rating }}</p>
        <p>Price: {{ activity.price }} €</p>
      </div>
    </div>
  </main>
</template>

<style lang="scss">
.activities {
  &__container {
    display: flex;
    flex-wrap: wrap;
    align-items: center;
    justify-content: center;
    margin: 0 auto;
    max-width: 1200px;
    padding: 0 20px;
  }
  &__activity {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 100%;
    max-width: 300px;
    margin: 0 20px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: center;
    transition: all 0.2s ease-in-out;
    &:hover {
      border: 1px solid #000;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
    }
  }
}
.search-input {
  width: 100%;
  max-width: 300px;
  margin-bottom: 20px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

@media screen and (min-width: 768px) {
  .activities {
    &__container {
      width: 100%;
      padding: 0;
      row-gap: 20px;
    }
  }
}
</style>

<script setup lang="ts">
import { ref, watch, computed, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";

interface Activity {
  id: number;
  title: string;
  price: number;
  currency: string;
  rating: number;
  specialOffer: boolean;
}

const route = useRoute();
const router = useRouter();

const activities = ref<Activity[]>([]);
const searchQuery = ref<string | null>(null);

const fetchActivities = (() => {
  let controller: AbortController | null = null;

  return async (query: string) => {
    if (controller) {
      controller.abort();
    }

    controller = new AbortController();
    const signal = controller.signal;

    try {
      const response = await fetch(
        `http://localhost:8080/api/activities?query=${encodeURIComponent(
          query
        )}`,
        { signal }
      );
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = await response.json();
      activities.value = data;
    } catch (error: any) {
      if (error.name === "AbortError") {
        // Request was aborted, no action needed
      } else {
        console.error("Error fetching activities:", error);
      }
    } finally {
      controller = null; // Reset the controller
    }
  };
})();

watch(
  () => route.query.query,
  (newQuery) => {
    searchQuery.value = (newQuery as string) || "";
    fetchActivities(searchQuery.value);
  }, { immediate: true }
);

watch(searchQuery, (newQuery) => {
  const isFirstSearch = !route.query.query;
  if (isFirstSearch) {
    router.push({ query: { query: newQuery } });
  } else {
    router.replace({ query: { query: newQuery } });
  }
});
</script>

<template>
  <div>
    <input
      default-value="route.query"
      class="search-input"
      v-model="searchQuery"
      placeholder="Search activities..."
    />
    <div class="activities__container" v-if="activities.length">
      <div
        v-for="activity in activities"
        :key="activity.id"
        class="activities__activity"
      >
        <h3>{{ activity.title }}</h3>
        <p>Price: {{ activity.price }} {{ activity.currency }}</p>
        <p>Rating: {{ activity.rating }}</p>
        <p v-if="activity.specialOffer">Special Offer Available!</p>
        <p>
          Supplier: {{ activity.supplierName }}, Location:
          {{ activity.location }}
        </p>
      </div>
    </div>
    <p v-else>No activities found.</p>
  </div>
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
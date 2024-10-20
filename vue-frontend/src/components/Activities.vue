<script setup lang="ts">
import { ref } from "vue";
import { useFetchActivities } from "../composables/useFetchActivities";
import { useWatchSearchParam } from "../composables/useWatchSearchParam";
import { useInfiniteScroll } from "../composables/useInfiniteScroll";

const { searchQuery } = useWatchSearchParam();
const hasSpecialOffer = ref(false);
const { activities, loading, error, loadMore, hasMore } = useFetchActivities(searchQuery, hasSpecialOffer);
const { lastItemRef } = useInfiniteScroll(loading, loadMore);
</script>

<template>
  <div>
    <div class="search-container">
      <input
        class="search-input"
        v-model="searchQuery"
        placeholder="Search activities..."
      />
      <label class="special-offer-checkbox">
        <input type="checkbox" v-model="hasSpecialOffer" />
        Has Special Offer
      </label>
    </div>
    <div class="activities__container" v-if="activities.length">
      <div
        v-for="(activity, index) in activities"
        :key="activity.id"
        class="activities__activity"
        v-bind="{
          ref: index === activities.length - 1 && hasMore ? lastItemRef : null,
        }"
      >
        <h3>{{ activity.title }}</h3>
        <p>Price: {{ activity.price }} {{ activity.currency }}</p>
        <p>Rating: {{ activity.rating }}</p>
        <p v-if="activity.specialOffer">Special Offer Available!</p>
        <p>Supplier: {{ activity.supplier.name }}</p>
        <p>Location: {{ activity.supplier.city }}, {{ activity.supplier.country }}</p>
      </div>
    </div>
    <div v-if="loading">Loading...</div>
    <div v-if="error">{{ error }}</div>
    <p v-if="!loading && !activities.length">No activities found.</p>
  </div>
</template>

<style lang="scss">
.activities {
  &__container {
    display: flex;
    flex-wrap: wrap;
    gap: 20px;
    align-items: flex-start;
    justify-content: center;
    margin: 0 auto;
    max-width: 1200px;
    padding: 0 20px;
  }
  &__activity {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    width: 100%;
    max-width: 300px;
    height: 350px;
    padding: 20px;
    border: 1px solid #ccc;
    border-radius: 5px;
    text-align: left;
    transition: all 0.2s ease-in-out;
    overflow: hidden;
    &:hover {
      border: 1px solid #000;
      box-shadow: 0 0 10px 0 rgba(0, 0, 0, 0.2);
    }
    h3 {
      margin-bottom: 10px;
    }
    p {
      margin: 5px 0;
    }
  }
}
.search-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
  gap: 20px;
  flex-wrap: wrap;
}
.search-input {
  width: 100%;
  max-width: 300px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.special-offer-checkbox {
  display: flex;
  align-items: center;
  font-size: 16px;
  input {
    margin-right: 5px;
  }
}

@media screen and (min-width: 768px) {
  .activities {
    &__container {
      padding: 0;
    }
    &__activity {
      flex: 1 1 calc(33.333% - 40px);
      margin: 10px;
    }
  }
}
</style>
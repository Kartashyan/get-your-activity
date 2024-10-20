// useFetchActivities.ts
import { ref, watch, toValue, Ref } from "vue";

type Activity = {
  id: string;
  title: string;
  price: number;
  currency: string;
  rating: number;
  specialOffer: boolean;
};

type ResponseData = {
  results: Activity[];
  next: string | null;
  total: number;
  totalPages: number;
};

export function useFetchActivities(searchQuery: Ref<string> | string) {
  const activities = ref<Activity[]>([]);
  const loading = ref(false);
  const error = ref<string | null>(null);
  const page = ref(1);
  const size = 10; // Adjust as needed
  const totalPages = ref(1);
  const hasMore = ref(true);

  const loadActivities = async (reset = false) => {
    if (loading.value || (!hasMore.value && !reset)) return;
    loading.value = true;
    error.value = null;

    try {
      const query = toValue(searchQuery);
      const response = await fetch(
        `http://localhost:8080/api/activities?query=${encodeURIComponent(query)}&page=${page.value}&size=${size}`
      );
      if (!response.ok) {
        throw new Error("Network response was not ok");
      }
      const data = (await response.json()) as ResponseData;

      if (reset) {
        activities.value = data.results;
      } else {
        activities.value = [...activities.value, ...data.results];
      }

      totalPages.value = data.totalPages;
      if (page.value >= totalPages.value) {
        hasMore.value = false;
      }
    } catch (err: any) {
      console.error("Error fetching activities:", err);
      error.value = err.message;
    } finally {
      loading.value = false;
    }
  };

  const loadMore = () => {
    if (!hasMore.value) return;
    page.value += 1;
    loadActivities();
  };

  // Watch for changes in searchQuery to reset activities and fetch new data
  watch(
    () => toValue(searchQuery),
    () => {
      activities.value = [];
      page.value = 1;
      hasMore.value = true;
      loadActivities(true);
    },
    { immediate: true }
  );

  return { activities, loading, error, loadMore, hasMore };
}
import { Ref, onUnmounted } from "vue";

export function useInfiniteScroll(
  loading: Ref<boolean>,
  loadMore: () => void,
  loadOffset: number = 0
) {
  let observer: IntersectionObserver | null = null;

  const lastItemRef = (el: Element | null) => {
    if (loading.value) return;
    if (observer) observer.disconnect();

    observer = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting) {
          loadMore();
        }
      },
      {
        rootMargin: `${loadOffset}px`,
      }
    );

    if (el) observer.observe(el);
  };

  onUnmounted(() => {
    if (observer) observer.disconnect();
  });

  return { lastItemRef };
}
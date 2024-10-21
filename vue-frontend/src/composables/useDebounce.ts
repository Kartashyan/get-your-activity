import { onUnmounted } from 'vue';

export function useDebounce<T extends (...args: any[]) => void>(fn: T, delay: number) {
  let timeoutId: ReturnType<typeof setTimeout> | null = null;

  const debouncedFunction = (...args: Parameters<T>) => {
    if (timeoutId !== null) {
      clearTimeout(timeoutId);
    }
    timeoutId = setTimeout(() => {
      fn(...args);
    }, delay);
  };

  onUnmounted(() => {
    if (timeoutId !== null) {
      clearTimeout(timeoutId);
    }
  });

  return debouncedFunction;
}
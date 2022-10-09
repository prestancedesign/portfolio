(ns portfolio.adapters.dumdom
  (:require [dumdom.core :as d]
            [portfolio.protocols :as portfolio]))

(def component-impl
  {`portfolio/render-component
   (fn [component el]
     (if el
       (d/render component el)
       (js/console.error "Asked to render Dumdom component without an element")))})

(defn create-scene [scene]
  (cond-> scene
    (:component scene) (update :component #(with-meta % component-impl))
    (:component-fn scene) (update :component-fn
                                  (fn [f]
                                    (fn [data]
                                      (with-meta (f data) component-impl))))))

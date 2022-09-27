package com.xsalefter.stripe;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentMethod;
import com.stripe.net.RequestOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class StripeCollectionTest {

    private static final String API_KEY = "sk_test_51Lfpr0Iptw41DXbHVacf7IUoQul6On9sB5rcfMP9atMVSwC1OWXHAgoK6qBXKYYu3vVnXohggVxEqMTmx2xTxB3p00m2V3QYDF";

    private RequestOptions createRequestOptions() {
        return RequestOptions.builder()
                .setApiKey(API_KEY)
                .build();
    }

    @AfterEach
    void afterEach() {
        // Needed when run test suite. Otherwise, Stripe.apiKey will set with
        // #testApiKeyUsingStripeGlobal() or #testApiKeyUsingRequestOptionsAndGlobal()
        Stripe.apiKey = null;
    }

    // This one will: com.stripe.exception.ApiKeyMissingException: API key is not set for autoPagingIterable.
    // You can set the API key globally using Stripe.ApiKey, or through RequestOptions with autoPagingIterable(params, options).
    @Test
    void testApiKeyInRequestOptions() throws StripeException {
        final Map<String, Object> paymentMethodParams = new HashMap<>();
        paymentMethodParams.put("type", "card");
        final RequestOptions requestOptions = createRequestOptions();

        final Iterable<PaymentMethod> result = PaymentMethod
                .list(paymentMethodParams, requestOptions)
                .autoPagingIterable();

        Assertions.assertNotNull(result);
    }

    @Test
    void testApiKeyUsingStripeGlobal() throws StripeException {
        Stripe.apiKey = API_KEY;

        final Map<String, Object> paymentMethodParams = new HashMap<>();
        paymentMethodParams.put("type", "card");
        final RequestOptions requestOptions = RequestOptions.builder().build(); // Set nothing

        final Iterable<PaymentMethod> result = PaymentMethod
                .list(paymentMethodParams, requestOptions)
                .autoPagingIterable();

        Assertions.assertNotNull(result);
    }

    @Test
    void testApiKeyUsingRequestOptionsAndGlobal() throws StripeException {
        Stripe.apiKey = API_KEY;

        final Map<String, Object> paymentMethodParams = new HashMap<>();
        paymentMethodParams.put("type", "card");
        final RequestOptions requestOptions = createRequestOptions();

        final Iterable<PaymentMethod> result = PaymentMethod
                .list(paymentMethodParams, requestOptions)
                .autoPagingIterable();

        Assertions.assertNotNull(result);
    }
}

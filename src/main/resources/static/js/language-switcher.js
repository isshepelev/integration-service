/**
 * Language Switcher for Integration Service
 * Handles Russian/English language toggle with localStorage persistence
 */
(function() {
    'use strict';

    var STORAGE_KEY = 'integration-service-lang';
    var DEFAULT_LANG = 'ru';

    var translations = {
        ru: {
            // Common
            'subtitle_main': 'МУЛЬТИПРОТОКОЛЬНЫЙ API ПОРТАЛ // 2050',
            'protocols_count': 'ПРОТОКОЛА',
            'entities': 'СУЩНОСТИ',
            'status': 'СТАТУС',
            'online': 'ОНЛАЙН',
            'active': 'АКТИВЕН',
            'all_systems': 'ВСЕ СИСТЕМЫ РАБОТАЮТ',
            'system_active': 'СИСТЕМА АКТИВНА',

            // Index page - sections
            'available_protocols': 'ДОСТУПНЫЕ ПРОТОКОЛЫ',
            'quick_links': 'БЫСТРЫЕ ССЫЛКИ',
            'api_endpoints': 'API Эндпоинты',
            'documentation': 'Документация',
            'server_info': 'Информация о сервере',
            'http_port': 'HTTP Порт',
            'grpc_port': 'gRPC Порт',

            // Protocol cards
            'rest_description': 'Спецификация OpenAPI 3.0 с Swagger UI для интерактивного исследования и тестирования.',
            'graphql_description': 'Гибкий язык запросов со встроенным GraphiQL IDE для разработки и тестирования.',
            'grpc_description': 'Высокопроизводительный RPC фреймворк с Protocol Buffers для эффективной коммуникации.',
            'soap_description': 'XML-протокол для корпоративной интеграции с WSDL описанием контракта.',
            'connection': 'Подключение',
            'examples': 'Примеры',
            'grpc_commands': 'GRPCURL КОМАНДЫ:',
            'list_services': '# Список сервисов',
            'get_products_comment': '# Получить продукты',

            // GraphQL Examples page
            'subtitle_graphql': 'ПРИМЕРЫ ЗАПРОСОВ // ГОД 2050',
            'queries': 'ЗАПРОСЫ',
            'types': 'ТИПЫ',
            'schema_overview': 'ОБЗОР СХЕМЫ',
            'synchronized': 'СИНХРОНИЗИРОВАНО',
            'entities_description': 'ОПИСАНИЕ СУЩНОСТЕЙ',
            'connection_info': 'ИНФОРМАЦИЯ О ПОДКЛЮЧЕНИИ',
            'host': 'Хост',
            'port': 'Порт',
            'endpoint': 'Эндпоинт',
            'transport': 'Транспорт',
            'full_url': 'Полный URL',
            'request_examples': 'ПРИМЕРЫ ЗАПРОСОВ',

            // Tab groups
            'products_group': 'ПРОДУКТЫ',
            'customers_group': 'КЛИЕНТЫ',
            'orders_group': 'ЗАКАЗЫ',
            'discovery_group': 'ОБНАРУЖЕНИЕ',

            // Tab titles
            'all_products': 'Все продукты',
            'with_availability': 'С наличием',
            'products_availability': 'Продукты + Наличие',
            'product_by_id': 'Продукт по ID',
            'product_by_sku': 'Продукт по SKU',
            'products_by_category': 'Продукты по категории',
            'create_product': 'Создать продукт',
            'update_product': 'Обновить продукт',
            'delete_product': 'Удалить продукт',
            'all_customers': 'Все клиенты',
            'customer_by_id': 'Клиент по ID',
            'create_customer': 'Создать клиента',
            'update_customer': 'Обновить клиента',
            'delete_customer': 'Удалить клиента',
            'all_orders': 'Все заказы',
            'orders_by_customer': 'Заказы по клиенту',
            'create_order': 'Создать заказ',
            'customer_orders': 'Клиент + Заказы',
            'update_status': 'Обновить статус',
            'delete_order': 'Удалить заказ',
            'list_services_tab': 'Список сервисов',
            'get_products': 'Получить продукты',
            'get_customers': 'Получить клиентов',
            'get_orders': 'Получить заказы',

            // Example headers
            'get_all_products': 'Получить все продукты',
            'products_with_availability': 'Продукты с наличием',
            'get_product_by_id': 'Получить продукт по ID',
            'get_all_customers': 'Получить всех клиентов',
            'get_customer_by_id': 'Получить клиента по ID',
            'create_customer_title': 'Создать клиента',
            'get_all_orders': 'Получить все заказы',
            'orders_by_customer_id': 'Заказы по ID клиента',
            'create_order_title': 'Создать заказ',
            'customer_with_orders': 'Клиент с заказами (Мульти-запрос)',
            'update_customer_title': 'Обновить клиента',
            'delete_customer_title': 'Удалить клиента',
            'update_order_status': 'Обновить статус заказа',
            'delete_order_title': 'Удалить заказ',
            'create_product_title': 'Создать продукт',
            'update_product_title': 'Обновить продукт',
            'delete_product_title': 'Удалить продукт',
            'available_services': 'Доступные сервисы',

            // Code labels
            'request': 'ЗАПРОС',
            'response': 'ОТВЕТ',
            'copy': 'КОПИРОВАТЬ',
            'copied': 'СКОПИРОВАНО',
            'grpcurl_command': 'GRPCURL КОМАНДА',
            'request_message': 'СООБЩЕНИЕ ЗАПРОСА',
            'open_in_playground': 'ОТКРЫТЬ В PLAYGROUND',

            // GraphQL Playground
            'subtitle_playground': 'PLAYGROUND // 2050',
            'query': 'ЗАПРОС',
            'execute': 'ВЫПОЛНИТЬ',
            'variables': 'ПЕРЕМЕННЫЕ',
            'response_label': 'ОТВЕТ',
            'quick_queries': 'БЫСТРЫЕ ЗАПРОСЫ (QUERY)',
            'mutations': 'МУТАЦИИ (MUTATION)',
            'execute_placeholder': 'Выполните запрос, чтобы увидеть ответ...',
            'executing': 'ВЫПОЛНЕНИЕ...',
            'success': 'УСПЕШНО',
            'error': 'ОШИБКА',
            'parse_error': 'Ошибка парсинга переменных: ',
            'network_error': 'Ошибка сети: ',
            'query_placeholder': 'Введите ваш GraphQL запрос здесь...',

            // gRPC Examples
            'subtitle_grpc': 'ПРИМЕРЫ RPC // ГОД 2050',
            'methods': 'МЕТОДЫ',
            'services': 'СЕРВИСЫ',
            'service_definitions': 'ОПРЕДЕЛЕНИЕ СЕРВИСОВ',
            'proto_definitions': 'PROTO ОПРЕДЕЛЕНИЯ',
            'service': 'Сервис',
            'full_path': 'Полный путь',
            'grpcurl_note': 'Используйте <code>grpcurl</code> для обнаружения доступных сервисов через gRPC reflection.',
            'no_params_note': 'Этот метод не требует параметров. Используйте <code>grpcurl</code> с пустым телом запроса <code>-d \'{}\'</code>',
            'download': 'СКАЧАТЬ',

            // SOAP Examples
            'subtitle_soap': 'XML ПРИМЕРЫ // ГОД 2050',
            'operations': 'ОПЕРАЦИИ',
            'wsdl_overview': 'ОБЗОР WSDL СХЕМЫ',

            // Footer
            'portal': 'PORTAL'
        },
        en: {
            // Common
            'subtitle_main': 'MULTI-PROTOCOL API PORTAL // 2050',
            'protocols_count': 'PROTOCOLS',
            'entities': 'ENTITIES',
            'status': 'STATUS',
            'online': 'ONLINE',
            'active': 'ACTIVE',
            'all_systems': 'ALL SYSTEMS OPERATIONAL',
            'system_active': 'SYSTEM ACTIVE',

            // Index page - sections
            'available_protocols': 'AVAILABLE PROTOCOLS',
            'quick_links': 'QUICK LINKS',
            'api_endpoints': 'API Endpoints',
            'documentation': 'Documentation',
            'server_info': 'Server Information',
            'http_port': 'HTTP Port',
            'grpc_port': 'gRPC Port',

            // Protocol cards
            'rest_description': 'OpenAPI 3.0 specification with Swagger UI for interactive exploration and testing.',
            'graphql_description': 'Flexible query language with built-in GraphiQL IDE for development and testing.',
            'grpc_description': 'High-performance RPC framework with Protocol Buffers for efficient communication.',
            'soap_description': 'XML protocol for enterprise integration with WSDL contract description.',
            'connection': 'Connection',
            'examples': 'Examples',
            'grpc_commands': 'GRPCURL COMMANDS:',
            'list_services': '# List services',
            'get_products_comment': '# Get products',

            // GraphQL Examples page
            'subtitle_graphql': 'QUERY EXAMPLES // YEAR 2050',
            'queries': 'QUERIES',
            'types': 'TYPES',
            'schema_overview': 'SCHEMA OVERVIEW',
            'synchronized': 'SYNCHRONIZED',
            'entities_description': 'ENTITY DESCRIPTIONS',
            'connection_info': 'CONNECTION INFO',
            'host': 'Host',
            'port': 'Port',
            'endpoint': 'Endpoint',
            'transport': 'Transport',
            'full_url': 'Full URL',
            'request_examples': 'REQUEST EXAMPLES',

            // Tab groups
            'products_group': 'PRODUCTS',
            'customers_group': 'CUSTOMERS',
            'orders_group': 'ORDERS',
            'discovery_group': 'DISCOVERY',

            // Tab titles
            'all_products': 'All Products',
            'with_availability': 'With Availability',
            'products_availability': 'Products + Availability',
            'product_by_id': 'Product by ID',
            'product_by_sku': 'Product by SKU',
            'products_by_category': 'Products by Category',
            'create_product': 'Create Product',
            'update_product': 'Update Product',
            'delete_product': 'Delete Product',
            'all_customers': 'All Customers',
            'customer_by_id': 'Customer by ID',
            'create_customer': 'Create Customer',
            'update_customer': 'Update Customer',
            'delete_customer': 'Delete Customer',
            'all_orders': 'All Orders',
            'orders_by_customer': 'Orders by Customer',
            'create_order': 'Create Order',
            'customer_orders': 'Customer + Orders',
            'update_status': 'Update Status',
            'delete_order': 'Delete Order',
            'list_services_tab': 'List Services',
            'get_products': 'Get Products',
            'get_customers': 'Get Customers',
            'get_orders': 'Get Orders',

            // Example headers
            'get_all_products': 'Get All Products',
            'products_with_availability': 'Products with Availability',
            'get_product_by_id': 'Get Product by ID',
            'get_all_customers': 'Get All Customers',
            'get_customer_by_id': 'Get Customer by ID',
            'create_customer_title': 'Create Customer',
            'get_all_orders': 'Get All Orders',
            'orders_by_customer_id': 'Orders by Customer ID',
            'create_order_title': 'Create Order',
            'customer_with_orders': 'Customer with Orders (Multi-query)',
            'update_customer_title': 'Update Customer',
            'delete_customer_title': 'Delete Customer',
            'update_order_status': 'Update Order Status',
            'delete_order_title': 'Delete Order',
            'create_product_title': 'Create Product',
            'update_product_title': 'Update Product',
            'delete_product_title': 'Delete Product',
            'available_services': 'Available Services',

            // Code labels
            'request': 'REQUEST',
            'response': 'RESPONSE',
            'copy': 'COPY',
            'copied': 'COPIED',
            'grpcurl_command': 'GRPCURL COMMAND',
            'request_message': 'REQUEST MESSAGE',
            'open_in_playground': 'OPEN IN PLAYGROUND',

            // GraphQL Playground
            'subtitle_playground': 'PLAYGROUND // 2050',
            'query': 'QUERY',
            'execute': 'EXECUTE',
            'variables': 'VARIABLES',
            'response_label': 'RESPONSE',
            'quick_queries': 'QUICK QUERIES (QUERY)',
            'mutations': 'MUTATIONS (MUTATION)',
            'execute_placeholder': 'Execute a query to see the response...',
            'executing': 'EXECUTING...',
            'success': 'SUCCESS',
            'error': 'ERROR',
            'parse_error': 'Variables parse error: ',
            'network_error': 'Network error: ',
            'query_placeholder': 'Enter your GraphQL query here...',

            // gRPC Examples
            'subtitle_grpc': 'RPC EXAMPLES // YEAR 2050',
            'methods': 'METHODS',
            'services': 'SERVICES',
            'service_definitions': 'SERVICE DEFINITIONS',
            'proto_definitions': 'PROTO DEFINITIONS',
            'service': 'Service',
            'full_path': 'Full Path',
            'grpcurl_note': 'Use <code>grpcurl</code> to discover available services via gRPC reflection.',
            'no_params_note': 'This method requires no parameters. Use <code>grpcurl</code> with empty request body <code>-d \'{}\'</code>',
            'download': 'DOWNLOAD',

            // SOAP Examples
            'subtitle_soap': 'XML EXAMPLES // YEAR 2050',
            'operations': 'OPERATIONS',
            'wsdl_overview': 'WSDL SCHEMA OVERVIEW',

            // Footer
            'portal': 'PORTAL'
        }
    };

    /**
     * Get saved language preference from localStorage
     */
    function getSavedLang() {
        try {
            return localStorage.getItem(STORAGE_KEY) || DEFAULT_LANG;
        } catch (e) {
            return DEFAULT_LANG;
        }
    }

    /**
     * Save language preference to localStorage
     */
    function saveLang(lang) {
        try {
            localStorage.setItem(STORAGE_KEY, lang);
        } catch (e) {
            // localStorage not available
        }
    }

    /**
     * Get current language
     */
    function getCurrentLang() {
        return getSavedLang();
    }

    /**
     * Apply translations to all elements with data-i18n attribute
     */
    function applyTranslations(lang) {
        var dict = translations[lang];
        if (!dict) return;

        document.querySelectorAll('[data-i18n]').forEach(function(element) {
            var key = element.getAttribute('data-i18n');
            if (dict[key]) {
                if (element.tagName === 'INPUT' || element.tagName === 'TEXTAREA') {
                    element.placeholder = dict[key];
                } else if (dict[key].indexOf('<code>') !== -1) {
                    element.innerHTML = dict[key];
                } else {
                    element.textContent = dict[key];
                }
            }
        });

        // Update html lang attribute
        document.documentElement.lang = lang;

        // Update button text
        updateButtonText();
    }

    /**
     * Update language switcher button text
     */
    function updateButtonText() {
        var button = document.querySelector('.language-switcher');
        if (button) {
            var currentLang = getCurrentLang();
            button.textContent = currentLang === 'ru' ? 'EN' : 'RU';
            button.setAttribute('title', currentLang === 'ru' ? 'Switch to English' : 'Переключить на русский');
        }
    }

    /**
     * Toggle between Russian and English
     */
    function toggleLanguage() {
        var currentLang = getCurrentLang();
        var newLang = currentLang === 'ru' ? 'en' : 'ru';
        saveLang(newLang);
        applyTranslations(newLang);
    }

    /**
     * Create and inject language switcher button
     */
    function createLanguageSwitcherButton() {
        if (document.querySelector('.language-switcher')) {
            return;
        }

        var button = document.createElement('button');
        button.className = 'language-switcher';
        button.setAttribute('type', 'button');

        var currentLang = getCurrentLang();
        button.textContent = currentLang === 'ru' ? 'EN' : 'RU';
        button.setAttribute('title', currentLang === 'ru' ? 'Switch to English' : 'Переключить на русский');

        button.addEventListener('click', toggleLanguage);
        document.body.appendChild(button);
    }

    /**
     * Initialize language on page load
     */
    function initLanguage() {
        var savedLang = getSavedLang();
        applyTranslations(savedLang);
    }

    /**
     * Main initialization
     */
    function init() {
        createLanguageSwitcherButton();
        initLanguage();
    }

    // Initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    // Expose functions globally
    window.toggleLanguage = toggleLanguage;
    window.getCurrentLang = getCurrentLang;
    window.applyTranslations = applyTranslations;
})();
